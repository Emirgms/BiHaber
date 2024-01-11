package com.devemir.bihaber.data.datasource

import com.devemir.bihaber.data.model.NewsApiResponse
import com.devemir.bihaber.remote.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteNewsDataSource {
    suspend fun getNewsHeadlines(country:String,pageNumber:Int): Response<NewsApiResponse>
    suspend fun getSearchNews(query:String): Flow<NetworkResult<NewsApiResponse>>
}