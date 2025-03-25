package com.shivayogi.epicnews.domain.repository

import androidx.paging.PagingData
import com.shivayogi.epicnews.data.models.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<PagingData<NewsArticle>>
    suspend fun bookmarkNews(article: NewsArticle)
    suspend fun removeBookmark(article: NewsArticle)
    fun getBookmarkedNews(): Flow<List<NewsArticle>>
    suspend fun getLikes(articleId: String): Int
    suspend fun getComments(articleId: String): Int
}
