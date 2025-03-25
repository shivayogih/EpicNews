package com.shivayogi.epicnews.presentation.navigation


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shivayogi.epicnews.data.models.NewsArticle
import com.shivayogi.epicnews.presentation.screens.DetailsScreen
import com.shivayogi.epicnews.presentation.screens.HomeScreen
import com.shivayogi.epicnews.presentation.viewmodel.NewsViewModel
import kotlinx.serialization.json.Json


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{newsArticle}") {
        fun passArticle(newsArticle: NewsArticle): String {
            val jsonArticle = Json.encodeToString(NewsArticle.serializer(), newsArticle)
            val encodedArticle = java.net.URLEncoder.encode(jsonArticle, "UTF-8")
            return "details/$encodedArticle"
        }
    }
}

@Composable
fun NavGraph(startDestination: String = Screen.Home.route) {
    val navController = rememberNavController()

    val viewModel: NewsViewModel = hiltViewModel()


    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("newsArticle") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedJson = backStackEntry.arguments?.getString("newsArticle")
            val json = encodedJson?.let { java.net.URLDecoder.decode(it, "UTF-8") }
            val article = json?.let { Json.decodeFromString<NewsArticle>(it) }

            article?.let {
                DetailsScreen(navController, it)
            } ?: Text("Error loading article")
        }
    }

}