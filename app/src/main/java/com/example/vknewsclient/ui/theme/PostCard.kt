package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vknewsclient.R

@Composable
fun PostCard() {
    Card(
        modifier = Modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            PostHeader()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.template_text))
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(R.drawable.post_content_image),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics()
        }
    }
}


@Composable
private fun PostHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            painter = painterResource(R.drawable.post_comunity_thumbnail),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "/dev/null", color = MaterialTheme.colorScheme.onPrimary)
            Text(text = "14:00", color = MaterialTheme.colorScheme.onSecondary)
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
private fun Statistics() {
    Row {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconPlusText((R.drawable.ic_eye2), 299)
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconPlusText((R.drawable.share2), 7)
            Spacer(modifier = Modifier.weight(1f))
            IconPlusText(R.drawable.comment2, 8)
            Spacer(modifier = Modifier.weight(1f))
            IconPlusText(R.drawable.like2, 23)
        }
    }
}

@Composable
private fun IconPlusText(image: Int, number: Int) {
    Icon(
        modifier = Modifier.size(21.dp),
        painter = painterResource(image),
        tint = MaterialTheme.colorScheme.onSecondary,
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
        text = "$number",
        color = MaterialTheme.colorScheme.onSecondary,
        fontSize = 17.sp,
        fontWeight = FontWeight.W300
    )
}


@Preview
@Composable
private fun PreviewLite() {
    VkNewsClientTheme(darkTheme = false, dynamicColor = false) {
        PostCard()
    }
}

@Preview
@Composable
private fun PreviewDark() {
    VkNewsClientTheme(darkTheme = true, dynamicColor = false) {
        PostCard()
    }
}