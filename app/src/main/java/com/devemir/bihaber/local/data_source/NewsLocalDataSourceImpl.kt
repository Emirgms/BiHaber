package com.devemir.bihaber.local.data_source

import com.devemir.bihaber.data.datasource.BaseRemoteNewsDataSource
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.db.NewsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class NewsLocalDataSourceImpl @Inject constructor (private val newsDao: NewsDao):NewsLocalDataSource,BaseRemoteNewsDataSource() {

    override fun getSavedNews(): Flow<List<Articles>> {
        return newsDao.getAllNews()
    }

    override suspend fun saveNews(articles: Articles) {
        return newsDao.insertAll(articles)
    }

    override suspend fun deleteNews(article: Articles) {
        if (article.url != null) {
            newsDao.deleteSaveNews(article.url)
        }

    }

    override fun isBookmarked(articleUrl: String): Boolean {
                return  newsDao.isBookmarked(articleUrl)
    }


}