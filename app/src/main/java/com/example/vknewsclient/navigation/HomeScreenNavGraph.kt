package com.example.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vknewsclient.domain.FeedPost
import com.google.gson.Gson

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route   // Вложенный граф навигации
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(     // Указываем напрямую ключ и тип аргумента
                navArgument(Screen.KEY_FEED_POST) {
                    type = FeedPost.NavigationType
                }
            )
        ) {// comments/{feed_post_id}
            val feedPost = it.arguments?.getParcelable<FeedPost>(Screen.KEY_FEED_POST) ?: throw RuntimeException("This args is null - Parcelable feedPost")
//            val feedPost = Gson().fromJson(feedPostJson, FeedPost::class.java)

//            val feedPostId = it.arguments?.getInt(Screen.KEY_FEED_POST_ID) ?: 0
//            val feedPostText = it.arguments?.getString(Screen.KEY_FEED_POST_TEXT) ?: ""
            commentsScreenContent(feedPost)
        }
    }
}
