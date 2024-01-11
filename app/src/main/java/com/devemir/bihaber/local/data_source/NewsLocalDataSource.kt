package com.devemir.bihaber.local.data_source

import com.devemir.bihaber.data.model.Articles
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
     fun getSavedNews(): Flow<List<Articles>>
    suspend fun saveNews(articles: Articles)
    suspend fun deleteNews(article: Articles)

    fun isBookmarked(articleUrl: String): Boolean

}