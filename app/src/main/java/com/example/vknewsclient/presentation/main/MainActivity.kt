package com.example.vknewsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme(dynamicColor = false) {
                val viewModel: MainViewModel = viewModel()
                val authState by viewModel.authState.collectAsState(AuthState.Initial)

                when (val state = authState) {
                    is AuthState.Initial -> {
                        LoginScreen { viewModel.authorize() }
                    }

                    is AuthState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is AuthState.Success -> {
                        MainScreen() // Переход на главный экран
                    }

                    is AuthState.Error -> {
                        LoginScreen(errorMessage = state.message) {
                            viewModel.authorize()
                        }
                    }
                }
            }
        }
    }
}








