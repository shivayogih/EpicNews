package com.shivayogi.epicnews.data.models



data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticle>
)