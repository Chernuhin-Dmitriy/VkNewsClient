package com.example.vknewsclient.ui.theme

import com.example.vknewsclient.domain.FeedPost

sealed class NewsFeedScreenState(){

    object Initial: NewsFeedScreenState() // Дефолтный скрин-стэйт заглушка

    data class Posts(val posts: List<FeedPost>): NewsFeedScreenState()
}
