package com.devemir.bihaber.ui.new_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    val repository: NewsRepository
):ViewModel() {
    private val _article = MutableLiveData<Articles?>(null)
    val article: LiveData<Articles?> get() = _article

    private val _isBookmarked = MutableLiveData<Boolean>(false)
    val isBookmarked: LiveData<Boolean> get() = _isBookmarked


    fun setArticle(article: Articles) {
        _article.value = article
        checkBookmark(article)
    }

    fun addBookmark() {
        if (_article.value == null) return
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveNewFromBookmarks(_article.value!!)
        }
    }

    fun removeBookmark() {
        if (_article.value == null) return
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNews(_article.value!!)
        }
    }

    private fun checkBookmark(article: Articles) {
        if (article.url == null) _isBookmarked.value = false
        viewModelScope.launch(Dispatchers.IO) {
            if (article.url == null) return@launch
            val deffered = async {
                repository.isBookmarked(article.url)
            }
            _isBookmarked.postValue(deffered.await())
        }
    }

}