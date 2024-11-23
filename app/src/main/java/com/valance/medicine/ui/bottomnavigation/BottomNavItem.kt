package com.valance.medicine.ui.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.valance.medicine.ui.fragment.OrderFragment
import com.valance.medicine.ui.fragment.ProfileFragment
import com.valance.medicine.ui.screens.MainScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    )
    {
        composable(BottomBarScreen.Home.route) {
            MainScreen()
        }

        composable(BottomBarScreen.Detail.route) {
            OrderFragment()
        }

        composable(BottomBarScreen.Profile.route){
            ProfileFragment()
        }
    }
}
