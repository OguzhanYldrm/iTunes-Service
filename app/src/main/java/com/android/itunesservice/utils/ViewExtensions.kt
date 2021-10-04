package com.android.itunesservice.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.itunesservice.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url : String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions()
        .centerCrop()
        .placeholder(placeholder)
        .error(R.drawable.product_placeholder)

    Glide
        .with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun notFoundPlaceholder(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 6f
        centerRadius = 35f
        start()
    }
}