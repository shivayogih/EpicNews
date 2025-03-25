package com.shivayogi.epicnews.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shivayogi.epicnews.data.models.NewsArticle
import com.shivayogi.epicnews.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val news: Flow<PagingData<NewsArticle>> = newsRepository.getNews().cachedIn(viewModelScope)

    private val _bookmarkedNews = MutableStateFlow<List<NewsArticle>>(emptyList())
    val bookmarkedNews: StateFlow<List<NewsArticle>> = _bookmarkedNews.asStateFlow()

    init {
        getBookmarkedNews()
    }

    private fun getBookmarkedNews() {
        viewModelScope.launch {
            newsRepository.getBookmarkedNews().collect { _bookmarkedNews.value = it
            }
        }
    }



    fun bookmarkArticle(article: NewsArticle) {
        viewModelScope.launch {
            val updatedArticle = article.copy(
                author = article.author ?: "Unknown Author", // Ensure author is not null
                isBookmarked = true
            )
            newsRepository.bookmarkNews(updatedArticle)
            _bookmarkedNews.value = _bookmarkedNews.value + updatedArticle
        }
    }

    fun removeBookmark(article: NewsArticle) {
        viewModelScope.launch {
            val updatedArticle = article.copy(
                author = article.author ?: "Unknown Author" // Ensure author is not null
            )
            newsRepository.removeBookmark(updatedArticle)
            _bookmarkedNews.value = _bookmarkedNews.value.filter { it.id != updatedArticle.id }
        }
    }

    fun getLikes(articleId: String, onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val likes = newsRepository.getLikes(articleId)
            onResult(likes)
        }
    }

    fun getComments(articleId: String, onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val comments = newsRepository.getComments(articleId)
            onResult(comments)
        }
    }
}
