package com.example.vknewsclient.presentation.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vknewsclient.R
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onViewClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikeClickListener: (StatisticItem) -> Unit
) {
    Card(
        modifier = modifier // Сочетание с настройками из вне
//            .background(MaterialTheme.colorScheme.background)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(8.dp),
                clip = false
            )
            .border(
                width = 0.5.dp,
                color = Color.LightGray.copy(alpha = 0.8f),
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            PostHeader(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedPost.contentText)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = feedPost.contentImageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                statistics = feedPost.statistics,
                model = feedPost,
                onViewClickListener = onViewClickListener,
                onShareClickListener = onShareClickListener,
                onCommentClickListener = onCommentClickListener,
                onLikeClickListener = onLikeClickListener
            )
        }
    }
}


@Composable
private fun PostHeader(
    feedPost: FeedPost
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = feedPost.communityImageUrl,
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = feedPost.communityName, color = MaterialTheme.colorScheme.onPrimary)
            Text(text = feedPost.publicationDate, color = MaterialTheme.colorScheme.onSecondary)
        }
        Icon(
            modifier = Modifier,
            imageVector = Icons.Rounded.MoreVert,
            tint = MaterialTheme.colorScheme.onSecondary,
            contentDescription = null
        )
    }
}


@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    model: FeedPost,
    onViewClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    onLikeClickListener: (StatisticItem) -> Unit
) {
    Row {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconPlusText(
                image = (R.drawable.ic_eye2),
                number = viewsItem.count.toString(),
                onItemClickListener = {
                    onViewClickListener(viewsItem)
                }
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARE)
            val commentsItem = statistics.getItemByType(StatisticType.COMMENT)
            val likesItem = statistics.getItemByType(StatisticType.LIKES)

            IconPlusText(
                (R.drawable.share2),
                sharesItem.count.toString(),
                onItemClickListener = {
                    onShareClickListener(sharesItem)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            IconPlusText(
                R.drawable.comment2,
                commentsItem.count.toString(),
                onItemClickListener = {
                    onCommentClickListener(commentsItem)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            IconPlusText(
                R.drawable.like2,
                likesItem.count.toString(),
                onItemClickListener = {
                    onLikeClickListener(likesItem)
                }
            )
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type }
        ?: throw IllegalStateException("List of StatisticItem hasn`t contain this TYPE")
}

@Composable
private fun IconPlusText(
    image: Int,
    number: String,
    onItemClickListener: () -> Unit
) {
    Icon(
        modifier = Modifier
            .size(25.dp)
            .clickable {
                onItemClickListener()
            },
        painter = painterResource(image),
        tint = MaterialTheme.colorScheme.onSecondary,
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
        text = number,
        color = MaterialTheme.colorScheme.onSecondary,
        fontSize = 17.sp,
        fontWeight = FontWeight.W300
    )
}


//@Preview
//@Composable
//private fun PreviewLite() {
//    VkNewsClientTheme(darkTheme = false, dynamicColor = false) {
//        PostCard()
//    }
//}
//
//@Preview
//@Composable
//private fun PreviewDark() {
//    VkNewsClientTheme(darkTheme = true, dynamicColor = false) {
//        PostCard()
//    }
//}