package com.devemir.bihaber.data.datasource

import com.devemir.bihaber.data.model.NewsApiResponse
import com.devemir.bihaber.remote.NetworkResult
import com.devemir.bihaber.remote.NewsApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

open class RemoteNewsDataSourceImpl @Inject constructor(private val newsApiService: NewsApiService):RemoteNewsDataSource,BaseRemoteNewsDataSource() {
    override suspend fun getNewsHeadlines(
        country: String,
        pageNumber: Int
    ): Response<NewsApiResponse> {
0
        return newsApiService.getNewsHeadLines(country,pageNumber)
    }

    override suspend fun getSearchNews(query: String): Flow<NetworkResult<NewsApiResponse>> {
        return getResult {
            newsApiService.getSearchNews(query)
        }
    }
}