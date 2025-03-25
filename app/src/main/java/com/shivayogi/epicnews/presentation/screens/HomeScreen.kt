package com.shivayogi.epicnews.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shivayogi.epicnews.data.models.NewsArticle
import com.shivayogi.epicnews.presentation.navigation.Screen
import com.shivayogi.epicnews.presentation.viewmodel.NewsViewModel
import com.shivayogi.epicnews.presentation.wigets.TabChip
import com.shivayogi.epicnews.presentation.wigets.TopBar


@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: NewsViewModel = hiltViewModel()
    var selectedTab by remember { mutableStateOf("All") }
    val news = viewModel.news.collectAsLazyPagingItems()
    val bookmarks by viewModel.bookmarkedNews.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Use common TopBar
        TopBar(
            title = "Epic News",
            shouldShowBackButton = false, // Menu Icon in HomeScreen
            onNavigationClick = { /* Handle menu click */ }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabChip("All", selectedTab == "All", Modifier.weight(1f)) { selectedTab = "All" }
            TabChip("Bookmarks", selectedTab == "Bookmarks", Modifier.weight(1f)) {
                selectedTab = "Bookmarks"
            }
        }
        // News List
        if (selectedTab == "All") {
            NewsList(news) { article ->
                navController.navigate(Screen.Details.passArticle(article))
            }
        } else {
            BookmarksList(bookmarks) { article ->
                navController.navigate(Screen.Details.passArticle(article))
            }
        }
    }
}

@Composable
fun NewsList(news: LazyPagingItems<NewsArticle>, onNewsClick: (NewsArticle) -> Unit) {
    val viewModel: NewsViewModel = hiltViewModel()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(news.itemCount) { index ->
            news[index]?.let { article ->
                NewsItem(article = article, onClick = onNewsClick)
            }
        }
    }
}

@Composable
fun BookmarksList(bookmarks: List<NewsArticle>, onNewsClick: (NewsArticle) -> Unit) {
    val viewModel: NewsViewModel = hiltViewModel()
    LazyColumn {
        items(bookmarks) { article ->
            NewsItem(article, onClick = onNewsClick)
        }
    }
}
