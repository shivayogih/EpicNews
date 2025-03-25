package com.shivayogi.epicnews.presentation.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shivayogi.epicnews.R
import com.shivayogi.epicnews.data.models.NewsArticle
import com.shivayogi.epicnews.presentation.viewmodel.NewsViewModel
import com.shivayogi.epicnews.presentation.wigets.TopBar



@Composable
fun DetailsScreen(navController: NavController, article: NewsArticle) {
    val viewModel: NewsViewModel = hiltViewModel()
    val bookmarkedNews by viewModel.bookmarkedNews.collectAsState()
    val isBookmarked = bookmarkedNews.any { it.id == article.id }

    Column(modifier = Modifier.fillMaxSize()) {
        // Common TopBar with Back Button
        TopBar(
            title = "Article Details",
            shouldShowBackButton = true,
            onNavigationClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {


            Box(modifier = Modifier.fillMaxWidth()) {
                // Article Image with Rounded Corners
                AsyncImage(
                    model = article.urlToImage ?: R.drawable.ic_placeholder,
                    contentDescription = "Article Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                // Bookmark Button at Bottom End
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomEnd // Aligns icon to bottom end
                ) {
                    IconButton(
                        onClick = {
                            if (isBookmarked) {
                                viewModel.removeBookmark(article)
                            } else {
                                viewModel.bookmarkArticle(article)
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White.copy(alpha = 0.7f), shape = CircleShape) // Background with transparency
                            .padding(4.dp) // Padding inside icon
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isBookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outline
                            ),
                            contentDescription = "Bookmark",
                            modifier = Modifier.size(32.dp),
                            tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                article.title?.let {
                    Text(
                        text = it,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Likes, Comments, and Bookmark Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_like), // Load from drawables
                            contentDescription = "Likes",
                            modifier = Modifier.size(32.dp) // Adjust size as needed
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${article.likes} likes")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_comment), // Load from drawables
                            contentDescription = "Comments",
                            modifier = Modifier.size(32.dp) // Adjust size as needed
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${article.comments} Comments")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Description
                Text(
                    text = article.description ?: "No description available.",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }


        }
    }
}
