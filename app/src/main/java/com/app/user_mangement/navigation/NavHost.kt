package com.app.user_mangement.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.klakmoum.navigation.NavDirections
import com.app.user_mangement.ui.screen.auth.login.PersonalizeScreen
import com.app.user_mangement.ui.screen.auth.login.RegisterScreen
import com.app.user_mangement.ui.screen.auth.login.SetupScreen
import com.app.user_mangement.ui.screen.auth.register.LoginScreen
import com.app.user_mangement.ui.screen.books.BookScreen
import com.app.user_mangement.ui.screen.dashboard.DashboardScreen
import com.app.user_mangement.ui.screen.qr_scan.QRScannerScreen
import com.app.user_mangement.ui.screen.welcome.WelcomeScreen
import org.example.user.management.sample.ui.screen.user.user_detail.CreateUserScreen


@Composable
fun AppNavGraph(
    startDestination: String,
    navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        userScreen(navController)
        createUserScreen(navController)
        scanQRScreen()
        loginScreen(navController)
        registerScreen(navController)
        bookListingScreen(navController)
        welcomeScreen(navController)
        appUserFunctionalityScreen(navController)
        createUsername(navController)
    }
}

private fun NavGraphBuilder.userScreen(navController: NavHostController) {
    composable(NavDirections.Dashboard.route) {
        DashboardScreen(
         navController
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
// Scan QR Code Screen
private fun NavGraphBuilder.scanQRScreen() {
    composable(NavDirections.ScanQRScreen.route) {
        QRScannerScreen {
        }
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

private  fun  NavGraphBuilder.bookListingScreen(navController: NavHostController){
    composable(NavDirections.BookListingScreen.route){
        BookScreen(navController = navController )
    }
}

private  fun NavGraphBuilder.welcomeScreen(navController: NavHostController) {
    composable(NavDirections.WelcomeScreen.route) {
       WelcomeScreen(navController = navController)
    }
}

private  fun NavGraphBuilder.appUserFunctionalityScreen(navController: NavHostController) {
    composable(NavDirections.AppUserFunctionalityScreen.route) {
        SetupScreen(navController = navController)
    }
}

private  fun NavGraphBuilder.createUsername(navController: NavHostController) {
    composable(NavDirections.UserNameScreen.route) {
        PersonalizeScreen(navController = navController)
    }
}