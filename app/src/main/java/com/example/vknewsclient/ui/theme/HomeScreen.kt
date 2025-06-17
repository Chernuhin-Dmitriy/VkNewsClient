package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vknewsclient.MainViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.PostComment

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when (val currentState = screenState.value) {
        is HomeScreenState.Posts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                posts = currentState.posts
            )
        }
        is HomeScreenState.Comments -> {
            CommentsScreen(
                feedPost = currentState.feedPost,
                comments = currentState.comments
            )
        }
        is HomeScreenState.Initial -> {}
    }
}

@Composable
private fun FeedPosts(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = { post -> post.id }
        ) { feedPost ->
            val dismissState = rememberSwipeToDismissBoxState(
                positionalThreshold = { distance: Float -> distance * 0.7f }
            )
            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                viewModel.delete(feedPost)
            }
            SwipeToDismissBox(
                modifier = Modifier.animateItem(),
                state = dismissState,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Red.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Delete Item",
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    }
                },
                enableDismissFromStartToEnd = false,
                content = {
                    PostCard(
                        modifier = Modifier,
//                        .padding(8.dp),
                        feedPost = feedPost,
                        onViewClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onShareClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onCommentClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onLikeClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        }
                    )
                }
            )
        }
    }
}