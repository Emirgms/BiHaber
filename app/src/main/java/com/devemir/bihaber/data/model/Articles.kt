package com.devemir.bihaber.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "articles")
@Parcelize
data class Articles(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @SerializedName("source")
    val source:Source?,
    @SerializedName("author")
    val author:String?,
    @SerializedName("title")
    val title:String?,
    @SerializedName("description")
    val description:String?,
    @SerializedName("url")
    val url:String?,
    @SerializedName("urlToImage")
    val urlToImage:String?,
    @SerializedName("publishedAt")
    val publishedAt:String?,
    @SerializedName("content")
    val content:String?,
    var isBookmarked:Boolean=false

) :Parcelable


