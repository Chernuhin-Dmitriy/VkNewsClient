package com.example.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vknewsclient.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.round

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel,
//    model: FeedPost,
//    onStatisticItemClickListener: (FeedPost, StatisticItem) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.background
            ) {
                val selectedItemPosition = remember { mutableStateOf(0) }
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )

                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value = index },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(item.titleResId))
                        },
                        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        },
    ) { paddingValues ->
        val feedPosts = viewModel.feedPosts.observeAsState(listOf())
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
                items = feedPosts.value,
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
}


@Composable
private fun ScaffoldExample() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Title") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { scaffoldState.drawerState.open() }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                }
            )
        },
        drawerContent = {
            Text("Drawer content", modifier = Modifier.padding(16.dp))
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        Text("Main content", modifier = Modifier.padding(padding))
    }
}