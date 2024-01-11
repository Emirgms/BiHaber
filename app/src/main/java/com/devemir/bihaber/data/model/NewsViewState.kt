package com.devemir.bihaber.data.model

data class NewsViewState(
    val isLoading:Boolean=true,
    val error:String="",
    val article:List<Articles>?=null
)
