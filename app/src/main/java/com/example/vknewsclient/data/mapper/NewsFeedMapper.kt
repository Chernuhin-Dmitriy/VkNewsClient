package com.example.vknewsclient.data.mapper

import com.example.vknewsclient.data.model.NewsFeedResponseDto
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        for(post in posts){
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break

            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                publicationDate = post.date.toString(),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(StatisticType.VIEWS, post.views.count),
                    StatisticItem(StatisticType.SHARE, post.reposts.count),
                    StatisticItem(StatisticType.COMMENT, post.comments.count),
                    StatisticItem(StatisticType.LIKES, post.comments.count)
                )
            )
            result.add(feedPost)
        }
        return result
    }
}