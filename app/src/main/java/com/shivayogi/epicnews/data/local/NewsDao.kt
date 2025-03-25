package com.shivayogi.epicnews.data.local


import androidx.room.*
import com.shivayogi.epicnews.data.models.NewsArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_articles")
    fun getBookmarkedNews(): Flow<List<NewsArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmarkNews(article: NewsArticle)

    @Delete
    suspend fun removeBookmark(article: NewsArticle)
}
