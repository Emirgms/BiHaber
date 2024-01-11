package com.devemir.bihaber.repository

import com.devemir.bihaber.data.datasource.paging_source.NewsRemotePagingSource
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.data.model.NewsApiResponse
import com.devemir.bihaber.remote.NetworkResult
import com.devemir.bihaber.util.Constants.DEFAULT_COUNTRY
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {
    suspend fun getNewsHeadLines(
        country: String=DEFAULT_COUNTRY,
        pageNumber: Int
    ):Response<NewsApiResponse>

     fun getNewsPagingSource():NewsRemotePagingSource
    suspend fun saveNewFromBookmarks(article: Articles)
   suspend fun getSavedNews():Flow<List<Articles>>
    suspend fun deleteNews(article: Articles)
   suspend fun isBookmarked(articleUrl: String): Boolean
    suspend fun getSearchNews(query:String):Flow<NetworkResult<NewsApiResponse>>
}