package com.shivayogi.epicnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.shivayogi.epicnews.data.models.NewsArticle
import com.shivayogi.epicnews.domain.repository.NewsRepository
import com.shivayogi.epicnews.presentation.viewmodel.NewsViewModel
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    // Executes tasks synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var newsViewModel: NewsViewModel
    private val newsRepository: NewsRepository = mockk()

    // Test coroutine dispatcher
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { newsRepository.getNews() } returns flowOf(PagingData.empty())
        every { newsRepository.getBookmarkedNews() } returns flowOf(emptyList())

        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `getBookmarkedNews() should update bookmarkedNews state`() = runTest {
        val articles = listOf(
            NewsArticle(id = "1", title = "News 1", isBookmarked = true),
            NewsArticle(id = "2", title = "News 2", isBookmarked = true)
        )
        every { newsRepository.getBookmarkedNews() } returns flowOf(articles)

        newsViewModel = NewsViewModel(newsRepository)
        advanceUntilIdle()

        assertEquals(articles, newsViewModel.bookmarkedNews.value)
    }

    @Test
    fun `bookmarkArticle() should add article to bookmarked list`() = runTest {
        val article = NewsArticle(id = "1", title = "News 1")
        coEvery { newsRepository.bookmarkNews(any()) } just Runs

        newsViewModel.bookmarkArticle(article)
        advanceUntilIdle()

        assertTrue(newsViewModel.bookmarkedNews.value.contains(article.copy(isBookmarked = true)))
    }

    @Test
    fun `removeBookmark() should remove article from bookmarked list`() = runTest {
        val article = NewsArticle(id = "1", title = "News 1", isBookmarked = true)
        coEvery { newsRepository.removeBookmark(any()) } just Runs

        newsViewModel.removeBookmark(article)
        advanceUntilIdle()

        assertTrue(newsViewModel.bookmarkedNews.value.none { it.id == article.id })
    }

    @Test
    fun `getLikes() should return correct like count`() = runTest {
        val articleId = "123"
        coEvery { newsRepository.getLikes(articleId) } returns 10

        var result = 0
        newsViewModel.getLikes(articleId) { result = it }
        advanceUntilIdle()

        assertEquals(10, result)
    }

    @Test
    fun `getComments() should return correct comment count`() = runTest {
        val articleId = "123"
        coEvery { newsRepository.getComments(articleId) } returns 5

        var result = 0
        newsViewModel.getComments(articleId) { result = it }
        advanceUntilIdle()

        assertEquals(5, result)
    }
}
