package com.app.klakmoum.navigation
sealed class NavDirections(val route: String) {
    object Dashboard : NavDirections("dashboard")
    object CreateUserScreen : NavDirections("create_user")

    // Update user screen with dynamic userId
    object UpdateUserScreen : NavDirections("update_user/{userId}") {
        fun createRoute(userId: Int) = "update_user/$userId"
    }
    //screen for scanning QR codes
    object ScanQRScreen : NavDirections("scan_qr")

    object SplashScreen : NavDirections("splash_screen")

    object  LoginScreen : NavDirections("login_screen")

    object RegisterScreen : NavDirections("register_screen")

}
