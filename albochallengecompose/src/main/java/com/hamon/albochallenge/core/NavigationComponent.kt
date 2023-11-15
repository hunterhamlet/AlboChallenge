package com.hamon.albochallenge.core


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hamon.albochallenge.features.practice_code_excercises.presentation.view.CodeExercisesScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object CodeExercices : Screen("code_excercises_screen")
}

@Composable
fun MainNavigationView() {

    // Navigation
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        // Home
        composable(Screen.Home.route) {
            HomeScreen(moveToStudioCaseScreen = {}, moveToCodeExercisesScreen = {
                navController.navigate(Screen.CodeExercices.route)
            })
        }

        //Code exercices
        // Home
        composable(Screen.CodeExercices.route) {
            CodeExercisesScreen()
        }
    }


}