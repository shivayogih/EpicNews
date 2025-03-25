package com.shivayogi.epicnews.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shivayogi.epicnews.data.models.NewsArticle
import com.shivayogi.epicnews.data.remote.CommentsLikesApiService
import com.shivayogi.epicnews.data.remote.NewsApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    private val apiService: NewsApiService,
    private val commentsLikesApiService: CommentsLikesApiService,
    private val country: String,
    private val apiKey: String
) : PagingSource<Int, NewsArticle>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getTopNews(country, apiKey)
            val articles = response.articles

            // Fetch likes and comments in parallel
            val updatedArticles = coroutineScope {
                articles.map { article ->
                    val articleId = getArticleIdFromUrl(article.url)

                    if (articleId.isNotBlank()) {
                        val likesDeferred = async { fetchLikes(articleId) }
                        val commentsDeferred = async { fetchComments(articleId) }

                        val likes = likesDeferred.await()
                        val comments = commentsDeferred.await()

                        article.copy(likes = likes, comments = comments)
                    } else {
                        article
                    }
                }
            }

            LoadResult.Page(
                data = updatedArticles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    private fun getArticleIdFromUrl(url: String): String {
        return url.removePrefix("https://")
            .removePrefix("http://")
            .replace("/", "-")
    }


    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? = state.anchorPosition

    private suspend fun fetchLikes(articleId: String): Int {
        return try {
           0 // commentsLikesApiService.getLikes(articleId).likes
        } catch (e: Exception) {
            0 // Default to 0 if error occurs
        }
    }

    private suspend fun fetchComments(articleId: String): Int {
        return try {
           0 // commentsLikesApiService.getComments(articleId).comments
        } catch (e: Exception) {
            0 // Default to 0 if error occurs
        }
    }
}
