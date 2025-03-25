package com.shivayogi.epicnews.data.remote


import com.shivayogi.epicnews.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopNews(@Query("country") country: String, @Query("apiKey") apiKey: String): NewsResponse
 }
