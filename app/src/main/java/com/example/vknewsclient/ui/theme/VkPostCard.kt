package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vknewsclient.R

@Preview(showBackground = true)
@Composable
fun VkPostCard() {
    Card(
        modifier = Modifier
            .padding(7.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Row {
                Image(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(8.dp)                    ,
                    verticalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(text = "уволено", fontSize = 17.sp, fontWeight = FontWeight.Medium)
                    Text(text = "14:00", fontSize = 17.sp)
                }
            }
            Image(
                modifier = Modifier
                    .size(25.dp),
                painter = painterResource(R.drawable.ic_threedots),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .padding(6.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            text = "кабаныч, когда узнал, что если сотрудникам не платить, они начинают умирать от голода"
        )
        Image(
            modifier = Modifier.
            size(380.dp),
            painter = painterResource(R.drawable.iv_rabbit),
            contentDescription = null
        )
        DownImagePanel()
    }
}


@Composable
fun DownImagePanel() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NumberAndImage(206, R.drawable.ic_eye)
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            NumberAndImage(206, R.drawable.ic_eye)
            NumberAndImage(206, R.drawable.ic_eye)
            NumberAndImage(206, R.drawable.ic_eye)
        }
    }
}

@Composable
fun NumberAndImage(number: Int, image: Int) {
    Row(
        modifier = Modifier
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = "$number", fontSize = 17.sp)
        Image(
            modifier = Modifier
                .padding(4.dp)
                .size(20.dp),
            painter = painterResource(image),
            contentDescription = null
        )
    }
}

