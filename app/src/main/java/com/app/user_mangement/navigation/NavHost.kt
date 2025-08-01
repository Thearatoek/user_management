package com.app.user_mangement.navigation

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
import com.app.klakmoum.navigation.NavDirections
import com.app.klakmoum.presentation.home.UserScreen
import com.app.klakmoum.ui.screen.user.user_update.UpdateUserScreen
import com.app.user_mangement.ui.screen.auth.login.LoginScreen
import com.app.user_mangement.ui.screen.auth.login.LoginViewModel
import com.app.user_mangement.ui.screen.auth.register.RegisterScreen
import com.app.user_mangement.ui.screen.qr_scan.QRScannerScreen
import com.app.user_mangement.ui.screen.splash_screen.SplashScreen
import com.app.user_mangement.ui.screen.user.user_list.LanguageViewModel
import com.app.user_mangement.ui.screen.user.user_list.ThemeModeViewModel
import org.example.user.management.sample.ui.screen.user.user_detail.CreateUserScreen
import org.example.user.management.sample.ui.screen.user.user_list.UserViewModel


@Composable
fun AppNavGraph(
    userViewModel: UserViewModel = hiltViewModel(), // or your own ViewModel
    loginViewModel: LoginViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel= hiltViewModel(),
    themeViewModel: ThemeModeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavDirections.SplashScreen.route
    ) {
        userScreen(navController, themeViewModel)
        createUserScreen(navController)
        updateUserScreen(navController, userViewModel)
        scanQRScreen()
        splashScreen(navController, loginViewModel)
        loginScreen(navController)
        registerScreen(navController)
    }
}

private fun NavGraphBuilder.userScreen(navController: NavHostController,  themeModeViewModel: ThemeModeViewModel) {
    composable(NavDirections.Dashboard.route) {
        UserScreen(
            viewModel = hiltViewModel(),
            navController = navController,
            themeModeViewModel = themeModeViewModel,
            languageViewModel = hiltViewModel(),
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

// Scan QR Code Screen
private fun NavGraphBuilder.scanQRScreen() {
    composable(NavDirections.ScanQRScreen.route) {
        QRScannerScreen {
        }
    }
}

// Splash Screen
private  fun NavGraphBuilder.splashScreen(navController: NavHostController,  userViewModel: LoginViewModel){

    composable(NavDirections.SplashScreen.route){
        SplashScreen(
            navController,
            userViewModel
        )
    }
}

private  fun  NavGraphBuilder.loginScreen(navController: NavHostController){
    composable(NavDirections.LoginScreen.route){
        LoginScreen(navController = navController )
    }
}
private  fun  NavGraphBuilder.registerScreen(navController: NavHostController){
    composable(NavDirections.RegisterScreen.route){
        RegisterScreen(navController = navController )
    }
}