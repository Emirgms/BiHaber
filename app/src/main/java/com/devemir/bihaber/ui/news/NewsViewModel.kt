package com.devemir.bihaber.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    application: Application
) : AndroidViewModel(application) {

    private lateinit var _pagingNews: Flow<PagingData<Articles>>
    val pagingNews: Flow<PagingData<Articles>> get() = _pagingNews
    init {
        getPagingData()
    }

    private fun getPagingData() {
       _pagingNews=Pager(
           config= PagingConfig(pageSize = 8, enablePlaceholders = false),
           pagingSourceFactory = {newsRepository.getNewsPagingSource()}
       ).flow.cachedIn(viewModelScope)
    }

    //    suspend fun getNewsHeadLines(){
//        newsRepository.getNewsHeadLines().collect() {
//            result ->
//            when(result){
//                is NetworkResult.Success -> {
//                    result.data?.let {
//
//                        NewsViewState(
//                            article = result.data.articles,
//                            isLoading = false
//
//                        )
//                    }!!.also {
//                        _newsState.value=it }
//
//                }
//                is NetworkResult.Error ->{
//                    _newsState.update {
//                        it.copy(
//                           error = result.message ?: "Hata",
//                            isLoading =false
//
//                        )
//                    }
//                }
//                is NetworkResult.Loading<*> -> {
//                    _newsState.update {
//                        it.copy(
//                            isLoading = true
//                        )
//                    }
//                }
//
//            }
//        }
//    }
    fun addBookmark(article: Articles) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.saveNewFromBookmarks(article)
        }
    }

    fun deleteBookmark(article: Articles) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteNews(article)
        }
    }

    fun isBookmarked(articleUrl: String): Boolean {
        if (articleUrl.isEmpty()) return false
        var checkBookmark = false
        runBlocking {

            withContext(Dispatchers.IO) {
                checkBookmark = newsRepository.isBookmarked(articleUrl)

            }
        }

        return checkBookmark
    }
}