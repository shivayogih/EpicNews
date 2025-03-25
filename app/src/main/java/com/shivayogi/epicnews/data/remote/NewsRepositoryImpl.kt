package com.shivayogi.epicnews.data.remote


//NewsDao, NewsArticle, NewsPagingSource, NewsRepository, NewsRepositoryImpl, di, NewsArticle,LikesResponse, CommentsResponse, NewsResponse, Resource, NewsApiService, viewmodel, domain usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.*
import com.shivayogi.epicnews.BuildConfig
import com.shivayogi.epicnews.data.local.NewsDao
import com.shivayogi.epicnews.data.models.NewsArticle
import com.shivayogi.epicnews.data.paging.NewsPagingSource
import com.shivayogi.epicnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val commentsLikesApiService: CommentsLikesApiService,
    private val newsDao: NewsDao
) : NewsRepository {

    override suspend fun getLikes(articleId: String): Int {
        return commentsLikesApiService.getLikes(articleId).likes
    }

    override suspend fun getComments(articleId: String): Int {
        return commentsLikesApiService.getComments(articleId).comments
    }

    override fun getNews(): Flow<PagingData<NewsArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(apiService,commentsLikesApiService, "us", BuildConfig.API_KEY) }
        ).flow
    }

    override suspend fun bookmarkNews(article: NewsArticle) {
        newsDao.bookmarkNews(article)
    }

    override suspend fun removeBookmark(article: NewsArticle) {
        newsDao.removeBookmark(article)
    }

    override fun getBookmarkedNews(): Flow<List<NewsArticle>> = newsDao.getBookmarkedNews()


}


