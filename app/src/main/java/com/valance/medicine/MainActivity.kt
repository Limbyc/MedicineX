package com.valance.medicine

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.fragment.NavHostFragment
import com.valance.medicine.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        navController.navigate(R.id.profileFragment)
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }


    @Composable
    fun NavHostContainer(
        navController: NavHostController,
        padding: PaddingValues
    ) {

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues = padding),
            builder = {

                // route : Home
                composable("home") {
                    navController.navigate(R.id.mainFragment)
                }

                // route : search
                composable("search") {
                    navController.navigate(R.id.orderFragment)
                }

                // route : profile
                composable("profile") {
                    navController.navigate(R.id.profileFragment)
                }
            })
    }



}
