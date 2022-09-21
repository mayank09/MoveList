package com.imdb.movieList.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.imdb.movieList.R

@BindingAdapter("load")
fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(this)
            .load(ImageBaseURL.plus(it))
            .into(this)
    }
}

@BindingAdapter("spanText")
fun TextView.spanRating(rating: Double){
   val spannable =  SpannableString(rating.toString().substring(0,3).plus("/10"))
    spannable.setSpan(
        ForegroundColorSpan(resources.getColor(R.color.teal_200)),
        0, 3,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    text = spannable
}