package com.shivayogi.epicnews

import androidx.paging.PagingData
import com.shivayogi.epicnews.data.local.NewsDao
import com.shivayogi.epicnews.data.models.NewsArticle
import com.shivayogi.epicnews.data.remote.CommentsLikesApiService
import com.shivayogi.epicnews.data.remote.NewsApiService
import com.shivayogi.epicnews.data.remote.NewsRepositoryImpl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class NewsRepositoryImplTest {

    private lateinit var newsRepository: NewsRepositoryImpl

    // Mock dependencies
    private val mockNewsApiService: NewsApiService = mock()
    private val mockCommentsLikesApiService: CommentsLikesApiService = mock()
    private val mockNewsDao: NewsDao = mock()

    @Before
    fun setUp() {
        newsRepository = NewsRepositoryImpl(mockNewsApiService, mockCommentsLikesApiService, mockNewsDao)
    }

    @Test
    fun `getLikes should return the correct number of likes`() = runBlocking {
        // Arrange
        val articleId = "123"
        `when`(mockCommentsLikesApiService.getLikes(articleId)).thenReturn(com.shivayogi.epicnews.data.models.LikesResponse(10))

        // Act
        val likes = newsRepository.getLikes(articleId)

        // Assert
        assertEquals(10, likes)
        verify(mockCommentsLikesApiService).getLikes(articleId)
    }

    @Test
    fun `getComments should return the correct number of comments`() = runBlocking {
        // Arrange
        val articleId = "456"
        `when`(mockCommentsLikesApiService.getComments(articleId)).thenReturn(com.shivayogi.epicnews.data.models.CommentsResponse(5))

        // Act
        val comments = newsRepository.getComments(articleId)

        // Assert
        assertEquals(5, comments)
        verify(mockCommentsLikesApiService).getComments(articleId)
    }

    @Test
    fun `bookmarkNews should insert article into database`() = runBlocking {
        // Arrange
        val article = NewsArticle(title = "Test Article", url = "https://example.com")

        // Act
        newsRepository.bookmarkNews(article)

        // Assert
        verify(mockNewsDao).bookmarkNews(article)
    }

    @Test
    fun `removeBookmark should delete article from database`() = runBlocking {
        // Arrange
        val article = NewsArticle(title = "Test Article", url = "https://example.com")

        // Act
        newsRepository.removeBookmark(article)

        // Assert
        verify(mockNewsDao).removeBookmark(article)
    }

    @Test
    fun `getBookmarkedNews should return bookmarked articles`() = runBlocking {
        // Arrange
        val articles = listOf(
            NewsArticle(title = "Article 1", url = "https://example.com/1"),
            NewsArticle(title = "Article 2", url = "https://example.com/2")
        )
        `when`(mockNewsDao.getBookmarkedNews()).thenReturn(flowOf(articles))

        // Act
        val result = newsRepository.getBookmarkedNews().first()

        // Assert
        assertEquals(articles, result)
    }
}
