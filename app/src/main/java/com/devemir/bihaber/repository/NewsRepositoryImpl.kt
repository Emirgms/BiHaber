package com.devemir.bihaber.repository

import com.devemir.bihaber.data.datasource.RemoteNewsDataSource
import com.devemir.bihaber.data.datasource.paging_source.NewsRemotePagingSource
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.data.model.NewsApiResponse
import com.devemir.bihaber.local.data_source.NewsLocalDataSource
import com.devemir.bihaber.remote.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: RemoteNewsDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {
    override suspend fun getNewsHeadLines(
        country: String,
        pageNumber: Int
    ): Response<NewsApiResponse> =
        newsRemoteDataSource.getNewsHeadlines(
            country,
            pageNumber
        )

    override  fun getNewsPagingSource() = NewsRemotePagingSource(
        newsRemoteDataSource
    )


    override suspend fun saveNewFromBookmarks(article: Articles) {
        return newsLocalDataSource.saveNews(article)
    }

    override suspend fun getSavedNews(): Flow<List<Articles>> {
        return newsLocalDataSource.getSavedNews()
    }

    override suspend fun deleteNews(article: Articles) {
        return newsLocalDataSource.deleteNews(article)
    }

    override suspend fun isBookmarked(articleUrl: String): Boolean {
        return newsLocalDataSource.isBookmarked(articleUrl)
    }

    override suspend fun getSearchNews(query: String): Flow<NetworkResult<NewsApiResponse>> {
        return newsRemoteDataSource.getSearchNews(query)
    }
}