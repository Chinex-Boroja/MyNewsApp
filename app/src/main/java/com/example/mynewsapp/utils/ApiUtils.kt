package com.example.mynewsapp.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mynewsapp.R
import com.example.mynewsapp.models.ArticleModel

fun shareNews(context: Context?, articleModel: ArticleModel) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, articleModel.urlToImage)
        putExtra(Intent.EXTRA_STREAM, articleModel.urlToImage)
        putExtra(Intent.EXTRA_TITLE, articleModel.title)
        type = "image/*"
    }
    context?.startActivity(Intent.createChooser(intent,"Share News On"))
}
//load image in image view
fun getCircularDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 48f
        setTint(context.resources.getColor(R.color.bgLineColor))
        start()
    }
}
fun ImageView.loadImage(url : String, progressDrawable: CircularProgressDrawable?) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_background)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}
@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView?, url: String?) {
    if (url != null) {
        imageView?.loadImage(url!!, getCircularDrawable(imageView.context))
    }
}