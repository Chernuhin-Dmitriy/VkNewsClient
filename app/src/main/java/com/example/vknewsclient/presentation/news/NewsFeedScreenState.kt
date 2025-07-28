package com.example.vknewsclient.presentation.news

import com.example.vknewsclient.domain.FeedPost

sealed class NewsFeedScreenState(){

    object Initial: NewsFeedScreenState() // Дефолтный скрин-стэйт заглушка

    data class Posts(val posts: List<FeedPost>): NewsFeedScreenState()
}
