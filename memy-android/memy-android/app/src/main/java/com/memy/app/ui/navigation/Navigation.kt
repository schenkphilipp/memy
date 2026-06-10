package com.memy.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    // Auth
    object Splash       : Screen("splash")
    object Welcome      : Screen("welcome")
    object Login        : Screen("login")
    object SignUp       : Screen("signup")
    object AuthSuccess  : Screen("auth_success")

    // Main (bottom nav)
    object Home         : Screen("home")
    object Collections  : Screen("collections")
    object Map          : Screen("map")
    object Profile      : Screen("profile")

    // Detail / sub-screens
    object Capture      : Screen("capture")
    object Detail       : Screen("detail/{memyId}") {
        fun createRoute(memyId: String) = "detail/$memyId"
    }
    object Album        : Screen("album/{collectionId}") {
        fun createRoute(collectionId: String) = "album/$collectionId"
    }
    object Search       : Screen("search")
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Home,        "Home",        Icons.Filled.Home,        Icons.Outlined.Home),
    BottomNavItem(Screen.Collections, "Collections", Icons.Filled.GridView,    Icons.Outlined.GridView),
    // Center slot is the FAB — not a nav item itself
    BottomNavItem(Screen.Map,         "Map",         Icons.Filled.Map,         Icons.Outlined.Map),
    BottomNavItem(Screen.Profile,     "Profile",     Icons.Filled.Person,      Icons.Outlined.Person),
)
