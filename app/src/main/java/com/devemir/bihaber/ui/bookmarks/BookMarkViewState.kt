package com.devemir.bihaber.ui.bookmarks

import com.devemir.bihaber.data.model.Articles
import java.lang.Error

data class BookMarkViewState(

    val isLoading:Boolean=true,
    val error:String="",
    val article:List<Articles>?=null)
