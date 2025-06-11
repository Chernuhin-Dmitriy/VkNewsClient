package com.example.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vknewsclient.ui.theme.MainScreen
import com.example.vknewsclient.ui.theme.NavigationItem
import com.example.vknewsclient.ui.theme.VkNewsClientTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme(dynamicColor = false) {
                Test(viewModel)
            }
        }
    }
}

@Composable
private fun Test(viewModel: MainViewModel) {
    VkNewsClientTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow),
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
                                androidx.compose.material.Icon(item.icon, contentDescription = null)
                            },
                            label = {
                                androidx.compose.material.Text(text = stringResource(item.titleResId))
                            },
                            selectedContentColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            },
        ) {
            val models = viewModel.models.observeAsState(listOf())
            LazyColumn(
                modifier = Modifier
                    .padding(it)
            ) {
                items(
                    items = models.value,
                    key = { post -> post.id }
                ) { model ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        positionalThreshold = { distance: Float -> distance * 0.7f }
                    )
                    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                        viewModel.delete(model)
                    }
                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp)
                                    .align(Alignment.CenterVertically)
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
                            MainScreen(
                                model = model,
                                onStatisticItemClickListener = { post, item ->
                                    viewModel.updateCount(post, item)
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun Example1() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedButton(onClick = {}) {
            Text(text = "Outline Button")
        }
    }
}

@Composable
private fun Example2() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val textValue = remember { mutableStateOf("") }

        TextField(
            value = "Value",
            onValueChange = {},
            label = { Text("Text Header") }
        )
    }
}

@Composable
private fun Example3() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val openDialog = remember { mutableStateOf(true) }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = false
                },
                title = { Text(text = "Are you shure?") },
                text = {
                    Text(
                        "This files are be deleted"
                    )
                },
                confirmButton = {
                    TextButton(onClick = { openDialog.value = false }) { Text("Yes") }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog.value = false }) { Text("No") }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Example4() {
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "TopAppBar title")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch { drawerState.open() }
                        }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(backgroundColor = Color.Yellow) {
                BottomNavigationItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    label = { Text(text = "Add") }
                )
                BottomNavigationItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Filled.Call, contentDescription = null) },
                    label = { Text(text = "Call") }
                )
                BottomNavigationItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Filled.Email, contentDescription = null) },
                    label = { Text(text = "E-mail") }
                )
            }
        }
    ) {
        Text(
            modifier = Modifier.padding(it),
            text = "This is scaffold content"
        )
    }
}

@Preview // Material 3
@Composable
fun ModalDrawerSample() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                onClick = { scope.launch { drawerState.close() } },
                content = { Text(text = "Click to Close") }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = if (drawerState.isClosed) ">>>Swipe To Open>>>" else "<<<Swipe To Close<<<")
                Spacer(modifier = Modifier.height(9.dp))
                Button(
                    onClick = { scope.launch { drawerState.open() } },
                    content = { Text(text = "Open") }
                )
            }
        }
    )
}













