package com.hamon.albochallenge.core.navigation


import ListToTaskScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hamon.albochallenge.features.home.presentation.view.HomeScreen
import com.hamon.albochallenge.features.practice_code_excercises.presentation.view.CodeExercisesScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object CodeExercicesScreen : Screen("code_excercises_screen")
    data object ListToTaskScreen : Screen("todo_list_screen")
}

@Composable
fun MainNavigationView() {

    // Navigation
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        // Home
        composable(Screen.Home.route) {
            HomeScreen(
                moveToNoteApp = { navController.navigate(Screen.ListToTaskScreen.route) },
                moveToCodeExercisesScreen = {
                    navController.navigate(Screen.CodeExercicesScreen.route)
                })
        }

        //Code exercices
        composable(Screen.CodeExercicesScreen.route) {
            CodeExercisesScreen()
        }

        //List to task
        composable(Screen.ListToTaskScreen.route) {
            ListToTaskScreen()
        }
    }


}