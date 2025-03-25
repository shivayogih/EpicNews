package com.shivayogi.epicnews.data.models



import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(tableName = "news_articles")
@Serializable
data class NewsArticle(
    @PrimaryKey val id: String = UUID.randomUUID().toString(), // Auto-generate if null/empty
    val title: String? = "",
    val description: String? = "",
    val url: String = "",
    val urlToImage: String? = "",
    val publishedAt: String = "",
    val content: String? = "",
    val author: String? = null,
    var isBookmarked: Boolean = false,
    var likes: Int = 0,
    var comments: Int = 0
) {
    companion object {
        fun fromApiResponse(apiResponse: NewsArticle): NewsArticle {
            return apiResponse.copy(
                id = if (apiResponse.id.isNullOrEmpty()) UUID.randomUUID().toString() else apiResponse.id
            )
        }
    }
}