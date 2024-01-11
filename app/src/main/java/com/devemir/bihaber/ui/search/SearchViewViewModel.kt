package com.devemir.bihaber.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.remote.NetworkResult
import com.devemir.bihaber.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val _searchState= MutableStateFlow(SearchViewState())
    val searchState=_searchState.asStateFlow()

     fun getSearchNews(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSearchNews(query).collect{result->
                when(result) {
                    is NetworkResult.Success -> {
                        result.data?.let {
                            SearchViewState(
                                article =result.data.articles,
                                isLoading = false

                            )
                        }!!.also {
                            _searchState.value=it
                        }

                    }
                    is NetworkResult.Loading ->{
                        _searchState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is NetworkResult.Error -> {
                        _searchState.update {
                            it.copy(
                                error = "Error verdim aga"
                            )
                        }
                    }
                }
            }
        }
    }
    fun addBookmark(article: Articles) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveNewFromBookmarks(article)
        }
    }
    fun deleteBookmark(article: Articles) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNews(article)
        }
    }
    fun isBookmarked(articleUrl: String): Boolean {
        if(articleUrl.isEmpty()) return false
        var checkBookmark = false
        runBlocking {

            withContext(Dispatchers.IO) {
                checkBookmark = repository.isBookmarked(articleUrl)
                println(checkBookmark.toString() + "check bookmark deger fonk ici ")
            }
        }

        println(checkBookmark.toString() + "check bookmark deger ")
        return checkBookmark
    }
}
