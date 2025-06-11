package com.example.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    model: FeedPost,
    onStatisticItemClickListener: (FeedPost, StatisticItem) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .size(670.dp)
            .systemBarsPadding(),
//        bottomBar = {
//            BottomNavigation(
//                backgroundColor = Color.DarkGray
//            ) {
//                val selectedItemPosition = remember { mutableStateOf(0) }
//                val items = listOf(
//                    NavigationItem.Home,
//                    NavigationItem.Favourite,
//                    NavigationItem.Profile
//                )
//
//                items.forEachIndexed { index, item ->
//                    BottomNavigationItem(
//                        selected = selectedItemPosition.value == index,
//                        onClick = { selectedItemPosition.value = index },
//                        icon = {
//                            Icon(item.icon, contentDescription = null)
//                        },
//                        label = {
//                            Text(text = stringResource(item.titleResId))
//                        },
//                        selectedContentColor = MaterialTheme.colorScheme.onSecondary,
//                        unselectedContentColor = MaterialTheme.colorScheme.onPrimary
//                    )
//                }
//            }
//        },
    ) {
//        val feedPost = viewModel.feedPost.observeAsState(FeedPost())
        PostCard(
            modifier = Modifier
                .padding(it),
            feedPost = model,
            onViewClickListener = { post, item ->
                onStatisticItemClickListener(post, item)
            },
            onShareClickListener = { post, item ->
                onStatisticItemClickListener(post, item)
            },
            onCommentClickListener = { post, item ->
                onStatisticItemClickListener(post, item)
            },
            onLikeClickListener = { post, item ->
                onStatisticItemClickListener(post, item)
            }
        )
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