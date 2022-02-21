package com.example.mynewsapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.MainActivity
import com.example.mynewsapp.R
import com.example.mynewsapp.adapters.ArticleAdapters
import com.example.mynewsapp.utils.Constants
import com.example.mynewsapp.utils.Resource
import com.example.mynewsapp.utils.shareNews
import com.example.mynewsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapters: ArticleAdapters
    val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        newsAdapters.setItemOnClickListener{
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment, bundle
            )
        }
        newsAdapters.onSaveClickListener {
            viewModel.insertArticle(it)
            Snackbar.make(requireView(), "Saved", Snackbar.LENGTH_SHORT).show()
        }

        newsAdapters.onDeleteClickListener {
            viewModel.deleteArticle(it)
            Snackbar.make(requireView(), "Deleted", Snackbar.LENGTH_SHORT).show()
        }
        newsAdapters.onShareClickListener {
            shareNews(context, it)
        }

        var searchJob : Job? = null
        etSearch.addTextChangedListener{ editable ->
            searchJob?.cancel()
            searchJob = MainScope().launch {
                delay(Constants.SEARCH_TIME_DELAY)
                editable?.let {
                    if (!editable.toString().trim().isEmpty()) {
                        viewModel.getSearchedNews(editable.toString())
                    }
                }
            }

        }
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { newResponse ->
            when(newResponse) {
                is Resource.Success -> {
                    shimmerFrag3.startShimmerAnimation()
                    shimmerFrag3.visibility = View.GONE
                    newResponse.data?.let { news ->
                        newsAdapters.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    shimmerFrag3.stopShimmerAnimation()
                    shimmerFrag3.visibility = View.GONE
                    newResponse.message?.let { message ->
                        Log.e(TAG, "Error :: $message")
                    }
                }
                is Resource.Loading -> {
                    shimmerFrag3.startShimmerAnimation()
                    shimmerFrag3.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun setupRecyclerView() {
        newsAdapters = ArticleAdapters()
        rVSearch_news.apply {
            adapter = newsAdapters
            layoutManager = LinearLayoutManager(activity)
        }
    }

}