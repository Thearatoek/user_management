package com.app.klakmoum.navigation
sealed class NavDirections(val route: String) {
    object Dashboard : NavDirections("dashboard")
    object  UserNameScreen : NavDirections("username_screen")
    object CreateUserScreen : NavDirections("create_user")
    object ScanQRScreen : NavDirections("scan_qr")

    object  LoginScreen : NavDirections("login_screen")

    object RegisterScreen : NavDirections("register_screen")

    object  BookListingScreen : NavDirections("book_listing_screen")

    object WelcomeScreen : NavDirections("welcome_screen")

    object  AppUserFunctionalityScreen : NavDirections("app_user_screen")

    object  GoogleMapScreen : NavDirections("google_map_screen")

    object  PermissionScreen : NavDirections("permission_screen")


}
