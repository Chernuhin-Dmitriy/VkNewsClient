package com.example.vknewsclient.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknewsclient.R

sealed class NavigationItem(
    val titleResId: Int,
    val icon: ImageVector
) {
    object Home : NavigationItem(
        titleResId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )

    object Favourite : NavigationItem(
        titleResId = R.string.navigation_item_favourite,
        icon = Icons.Outlined.FavoriteBorder
    )

    object Profile : NavigationItem(
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Face
    )
}