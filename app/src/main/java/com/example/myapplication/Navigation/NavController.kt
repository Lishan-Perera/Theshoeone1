package com.example.myapplication.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.DataSource
import com.example.myapplication.UIs.Cart
import com.example.myapplication.UIs.Home
import com.example.myapplication.UIs.Login
import com.example.myapplication.UIs.MasterView
import com.example.myapplication.UIs.ProductsScreen
import com.example.myapplication.UIs.ProfileScreen
import com.example.myapplication.UIs.SignUp
import com.example.myapplication.UIs.getStarted

@Composable
fun Navigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "getstarted", builder = {
        composable("getstarted"){
            getStarted(navController);
        }
        composable("signup"){
            SignUp(navController);
        }
        composable("login"){
            Login(modifier, navController);
        }
        composable("home"){
            Home(navController);
        }
        composable("cart"){
            Cart(navController)
        }
        composable("profile"){
            ProfileScreen(navController)
        }
        composable("products"){
            ProductsScreen(navController)
        }
        composable("masterview/{imageResourceId}") { backStackEntry ->
            val imageResourceId = backStackEntry.arguments?.getString("imageResourceId")?.toInt()
            val product = DataSource().loadTrending().find { it.imagesResourceId == imageResourceId }
                ?: DataSource().loadNike().find { it.imagesResourceId == imageResourceId }

            product?.let {
                MasterView(product = it, navController = navController)
            }
        }



    })
}