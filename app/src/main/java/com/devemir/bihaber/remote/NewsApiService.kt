package com.devemir.bihaber.remote

import com.devemir.bihaber.data.model.NewsApiResponse
import com.devemir.bihaber.util.Constants.API_KEY
import com.devemir.bihaber.util.Constants.DEFAULT_COUNTRY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    // ebd1249a63bf4b6282404a1bb4e0e40e
    @GET("v2/top-headlines")
    suspend fun getNewsHeadLines(
        @Query("country")
        country:String="en",

        @Query("page")
        page:Int=1,

        @Query("apiKey")
        apiKey:String= API_KEY
    ): Response<NewsApiResponse>

    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        query:String,
        @Query("apiKey")
        apiKey:String= API_KEY,
        @Query("searchIn")
        searchIn:String="title",
        ):Response<NewsApiResponse>

}