package com.example.vknewsclient.domain

import com.example.vknewsclient.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val avatarId: Int = R.drawable.avatar,
    val commentText: String = "Long comment text",
    val publicationDate: String = "14:00"
)
