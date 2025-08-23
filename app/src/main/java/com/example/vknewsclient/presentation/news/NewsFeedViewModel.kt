package com.example.vknewsclient.presentation.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknewsclient.data.mapper.NewsFeedMapper
import com.example.vknewsclient.data.network.ApiFactory
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.refresh.VKIDRefreshTokenCallback
import com.vk.id.refresh.VKIDRefreshTokenFail
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFeedViewModel : ViewModel() {

    private val initialState = NewsFeedScreenState.Initial
    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val mapper = NewsFeedMapper()

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            try {
                val token = VKID.instance.accessToken?.token ?: return@launch
                val response = ApiFactory.apiService.loadRecommendations(token)
                val feedPost = mapper.mapResponseToPosts(response)
                _screenState.value = NewsFeedScreenState.Posts(posts = feedPost)
            } catch(e: Exception) {
                refresh()
                delay(3000)
                val token = VKID.instance.accessToken?.token ?: return@launch
                val response = ApiFactory.apiService.loadRecommendations(token)
                val feedPost = mapper.mapResponseToPosts(response)
                _screenState.value = NewsFeedScreenState.Posts(posts = feedPost)
            }
        }
    }

    private val vkRefreshCallback = object : VKIDRefreshTokenCallback {
        override fun onSuccess(token: AccessToken) {
            // Использование AT
        }
        override fun onFail(fail: VKIDRefreshTokenFail) {
            when (fail) {
                is VKIDRefreshTokenFail.FailedApiCall -> fail.description // Использование текста ошибки.
                is VKIDRefreshTokenFail.FailedOAuthState -> fail.description // Использование текста ошибки.
                is VKIDRefreshTokenFail.RefreshTokenExpired -> fail.description // Ошибка истечения срока жизни RT. Это уведомление о том, что пользователю нужно перелогиниться.
                is VKIDRefreshTokenFail.NotAuthenticated -> fail.description // Ошибка отсутствия авторизации у пользователя. Это уведомление о том, что пользователю нужно авторизоваться.
            }
        }
    }

    // Для обновления токена
    fun refresh() {
        viewModelScope.launch {
            try {
                VKID.instance.refreshToken(
                    callback = vkRefreshCallback
                )
            } catch (e: Exception) {
                Log.d("auth", "Perhaps token has expired ...")
            }
        }
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        val oldStatistic = feedPost.statistics

        val newStatistic = oldStatistic.toMutableList().apply{
            replaceAll {oldItem ->
                if(oldItem.type == item.type) {
                    item.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistic)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun remove(model: FeedPost) {
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(model)
        _screenState.value = NewsFeedScreenState.Posts(posts = oldPosts)
    }
}

