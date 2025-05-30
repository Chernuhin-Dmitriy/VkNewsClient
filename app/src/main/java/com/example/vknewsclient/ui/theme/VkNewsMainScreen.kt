package com.example.vknewsclient.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldDefaults
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.R
import kotlinx.coroutines.launch

@Preview
@Composable
fun MainScreen() {
    val selectedItemPosition = remember { mutableStateOf(0) }
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Favourite,
        NavigationItem.Profile
    )
    val snackbarHostState = SnackbarHostState()
    Log.d("MainScreen", snackbarHostState.currentSnackbarData.toString())
    val scope = rememberCoroutineScope()
    val fabIsVisible = remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.DarkGray
            ) {
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
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            if(fabIsVisible.value){
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            val action = snackbarHostState.showSnackbar(
                                message = "My first Snackbar",
                                actionLabel = "Hide FAB",
                                duration = SnackbarDuration.Short
                            )
                            if(action == SnackbarResult.ActionPerformed) {
                                fabIsVisible.value = false
                            }
                        }
                    }
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
            }
        },
    ) { paddingValues ->
        Text(text = "Scaffold text", Modifier.padding(paddingValues))
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