package com.example.mynewsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewsapp.MainActivity
import com.example.mynewsapp.R
import com.example.mynewsapp.adapters.ArticleAdapters
import com.example.mynewsapp.utils.Resource
import com.example.mynewsapp.utils.shareNews
import com.example.mynewsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlin.random.Random

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapters: ArticleAdapters
    val TAG = "BreakingNewsFragment"

    private fun setupRecyclerView() {
        newsAdapters = ArticleAdapters()
        rViewBreakingNews.apply {
            adapter = newsAdapters
            layoutManager = LinearLayoutManager(activity)
        }
        newsAdapters.setItemOnClickListener{
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment, bundle
            )
        }
        newsAdapters.onSaveClickListener {
            if (it.id == null){
                it.id = Random .nextInt(0, 1000)
            }
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        setViewModelObserver()
    }

    private fun setViewModelObserver() {
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    shimmerFrag.stopShimmerAnimation()
                    shimmerFrag.visibility = View.GONE
                    newsResponse.data?.let {news ->
                        rViewBreakingNews.visibility = View.VISIBLE
                        newsAdapters.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    shimmerFrag.visibility = View.GONE
                    newsResponse.message?.let { message ->
                        Log.e(TAG, "Error :: $message")
                    }
                }
                is Resource.Loading -> {
                    shimmerFrag.startShimmerAnimation()
                }
            }
        })
    }


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_breaking_news, container, false)
//    }

}