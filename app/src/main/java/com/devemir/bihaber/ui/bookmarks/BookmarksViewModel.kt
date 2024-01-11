package com.devemir.bihaber.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repository: NewsRepository

) : ViewModel() {
    private val _bookMarkState = MutableStateFlow(BookMarkViewState())
    val bookMarkState = _bookMarkState.asStateFlow()
    fun getSavedNews() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSavedNews()
                .onStart {
                    _bookMarkState.value = BookMarkViewState(
                        isLoading = true
                    )
                }.catch {
                    _bookMarkState.value = BookMarkViewState(
                        error = "Error",
                        isLoading = false
                    )
                }
                .collect() { result ->
                    _bookMarkState.value = BookMarkViewState(
                        isLoading = false,
                        article = result,
                        error = ""
                    )

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

            }
        }

        return checkBookmark
    }
}