package com.example.mynewsapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.MainActivity
import com.example.mynewsapp.R
import com.example.mynewsapp.adapters.ArticleAdapters
import com.example.mynewsapp.adapters.SavedNewsAdapters
import com.example.mynewsapp.utils.Resource
import com.example.mynewsapp.utils.shareNews
import com.example.mynewsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlin.random.Random

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapters: SavedNewsAdapters
    val TAG = "SavedNewsFragment"

    private fun setupRecyclerView() {
        newsAdapters = SavedNewsAdapters()
        rVSaved_news.apply {
            adapter = newsAdapters
            layoutManager = LinearLayoutManager(activity)
        }
        newsAdapters.setItemOnClickListener{
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment, bundle
            )
        }

        newsAdapters.onShareClickListener {
            shareNews(context, it)
        }
        //swipe to delete
        val onItemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapters.differ.currentList[position]
                viewModel.deleteArticle(article)

                Snackbar.make(requireView(), "Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.insertArticle(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(onItemTouchHelperCallback).apply {
            attachToRecyclerView(rVSaved_news)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        setViewModelObserver()
    }

    private fun setViewModelObserver() {
        viewModel = (activity as MainActivity).viewModel
        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
            Log.i(TAG, ""+ it.size)
            newsAdapters.differ.submitList(it)
            rVSaved_news.visibility = View.VISIBLE
            shimmerFrag2.stopShimmerAnimation()
            shimmerFrag2.visibility = View.GONE
        })
    }
}