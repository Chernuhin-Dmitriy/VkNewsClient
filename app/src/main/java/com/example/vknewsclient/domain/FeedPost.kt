package com.example.vknewsclient.domain

import com.example.vknewsclient.R

data class FeedPost(
    val id: Int,
    val communityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 966),
        StatisticItem(StatisticType.SHARE, 7),
        StatisticItem(StatisticType.COMMENT, 23),
        StatisticItem(StatisticType.LIKES, 48)
    )
)
