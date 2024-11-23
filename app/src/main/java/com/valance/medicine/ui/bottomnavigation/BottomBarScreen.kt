package com.valance.medicine.ui.bottomnavigation

import androidx.annotation.DrawableRes
import com.valance.medicine.R

sealed class BottomBarScreen(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int,
) {

    data object Home : BottomBarScreen(
        route = "main_screen",
        label = "Home",
        icon = R.drawable.baseline_home_24,
    )

    data object Detail : BottomBarScreen(
        route = "order_screen",
        label = "Order",
        icon = R.drawable.order_rbbvy9wmcfay,
    )

    data object Profile : BottomBarScreen(
        route = "profile_fragment",
        label = "Profile",
        icon = R.drawable.baseline_person_24
    )

}