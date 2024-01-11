package com.devemir.bihaber.ui.search

import com.devemir.bihaber.data.model.Articles

data class SearchViewState(
    val isLoading:Boolean=true,
    val error:String="",
    val article:List<Articles>?=null
)
