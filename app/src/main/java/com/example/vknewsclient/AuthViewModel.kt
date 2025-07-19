package com.example.vknewsclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    //State экрана авторизации
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState

    private val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            _authState.value = AuthState.Success(accessToken.token)
        }

        override fun onFail(fail: VKIDAuthFail) {
            _authState.value = when (fail) {
                is VKIDAuthFail.Canceled -> AuthState.Error("Авторизация отменена")
                else -> AuthState.Error("Ошибка авторизации: ${fail.description}")
            }
        }
    }

    //При нажатии на авторизацию
    fun authorize() {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try{
                VKID.instance.authorize(vkAuthCallback)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Ошибка: ${e.message}")
            }
        }
    }
}


sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}