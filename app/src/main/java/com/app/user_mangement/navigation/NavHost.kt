package com.app.klakmoum.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.klakmoum.presentation.home.UserScreen
import com.app.klakmoum.ui.screen.user.user_update.UpdateUserScreen
import org.example.user.management.sample.ui.screen.user.user_detail.CreateUserScreen
import org.example.user.management.sample.ui.screen.user.user_list.UserViewModel


@Composable
fun AppNavGraph(
    userViewModel: UserViewModel = hiltViewModel(), // or your own ViewModel
    navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavDirections.Dashboard.route
    ) {
        userScreen(navController)
        createUserScreen(navController)
        updateUserScreen(navController, userViewModel)
    }
}

private fun NavGraphBuilder.userScreen(navController: NavHostController) {
    composable(NavDirections.Dashboard.route) {
        UserScreen(
            viewModel = hiltViewModel(),
            navController = navController
        )
    }
}


private fun NavGraphBuilder.createUserScreen(navController: NavHostController) {
    composable(NavDirections.CreateUserScreen.route) {
        CreateUserScreen(
            viewModel = hiltViewModel(),
            navController = navController
        )
    }
}
fun NavGraphBuilder.updateUserScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    composable(
        route = NavDirections.UpdateUserScreen.route,
        arguments = listOf(navArgument("userId") { type = NavType.IntType })
    ) { backStackEntry ->
        val userId = backStackEntry.arguments?.getInt("userId") ?: -1
        Log.d("Navigation", "Navigated to UpdateUserScreen with userId = $userId")

        UpdateUserScreen(
            userId = userId,
            viewModel = userViewModel,
            navController = navController
        )
    }
}
