package com.example.vknewsclient.domain

data class StatisticItem(
    val type: StatisticType,
    val count: Int = 0
) {
}

enum class StatisticType{
    VIEWS, SHARE, COMMENT, LIKES
}