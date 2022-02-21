package com.example.mynewsapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Util
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.ItemArticleBinding
import com.example.mynewsapp.models.ArticleModel

class SavedNewsAdapters : RecyclerView.Adapter<SavedNewsAdapters.SavedNewsViewHolder>() {

    private val diffUtilCallback = object : DiffUtil.ItemCallback<ArticleModel>() {

        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.url == oldItem.url
        }

        override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class SavedNewsViewHolder(var view: ItemArticleBinding) :
        RecyclerView.ViewHolder(view.root)

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemArticleBinding>(
            inflater,
            R.layout.item_saved_news,
            parent,
            false
        )
        return SavedNewsViewHolder(view)
    }
    override fun getItemCount() = differ.currentList.size

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.view.article = article

        //item click listener
        //bind these click listener later
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                article.let { article ->
                    it(article)
                }
            }
        }

        holder.view.ivShare.setOnClickListener {
            onShareNewsClick?.let {
                article.let { it1 ->
                    it(it1)
                }
            }
        }

    }

    private var onItemClickListener: ((ArticleModel) -> Unit)? = null
    private var onShareNewsClick: ((ArticleModel) -> Unit)? = null

    fun setItemOnClickListener(listener: (ArticleModel) -> Unit) {
        onItemClickListener = listener
    }

    fun onShareClickListener(listener: (ArticleModel) -> Unit) {
        onShareNewsClick = listener
    }
}