package com.devemir.bihaber.util

import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devemir.bihaber.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.parseISODate(): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yy - HH:mm:ss")
    val dateTime = LocalDateTime.parse(this, inputFormatter)
    return dateTime.format(outputFormatter)
}

fun AppCompatImageView.getImageFromUrl(url: String) {
   // val uri: Uri = Uri.parse(url)
    Glide.with(this)
        .load(url)
      //  .fitCenter()
        .into(this)
}