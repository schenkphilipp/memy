package com.memy.app

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.memy.app.ui.components.CoralFAB
import com.memy.app.ui.navigation.*
import com.memy.app.ui.screens.*
import com.memy.app.ui.theme.*

// Screen-level transition spec matching the design spec: fade + 8px rise, 360ms
private val enterTransition: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
    fadeIn(tween(360)) + slideInVertically(tween(360)) { 24 }
}
private val exitTransition: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
    fadeOut(tween(220))
}

@Composable
fun MemyApp(viewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mainRoutes = setOf(
        Screen.Home.route, Screen.Collections.route,
        Screen.Map.route,  Screen.Profile.route,
    )
    val showBottomBar = currentRoute in mainRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                MemyBottomBar(
                    currentRoute = currentRoute,
                    onNavigate   = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    },
                    onFabClick = {
                        navController.navigate(Screen.Capture.route)
                    }
                )
            }
        },
        containerColor = SurfacePage,
    ) { innerPadding ->
        NavHost(
            navController    = navController,
            startDestination = Screen.Splash.route,
            modifier         = Modifier.padding(innerPadding),
            enterTransition  = enterTransition,
            exitTransition   = exitTransition,
        ) {
            // ── Auth ──────────────────────────────────────────────────────
            composable(Screen.Splash.route) {
                SplashScreen(onComplete = {
                    if (isLoggedIn)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    else
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                })
            }
            composable(Screen.Welcome.route) {
                WelcomeScreen(onContinue = { navController.navigate(Screen.Login.route) })
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    onLogin  = {
                        viewModel.login()
                        navController.navigate(Screen.AuthSuccess.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    },
                    onSignUp = { navController.navigate(Screen.SignUp.route) }
                )
            }
            composable(Screen.SignUp.route) {
                SignUpScreen(
                    onCreateAccount = {
                        viewModel.login()
                        navController.navigate(Screen.AuthSuccess.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    },
                    onLogin = { navController.navigateUp() }
                )
            }
            composable(Screen.AuthSuccess.route) {
                AuthSuccessScreen(onContinue = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.AuthSuccess.route) { inclusive = true }
                    }
                })
            }

            // ── Main tabs ─────────────────────────────────────────────────
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel    = viewModel,
                    onMemyClick  = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                    onSearchClick = { navController.navigate(Screen.Search.route) },
                )
            }
            composable(Screen.Collections.route) {
                CollectionsScreen(
                    viewModel           = viewModel,
                    onCollectionClick   = { id -> navController.navigate(Screen.Album.createRoute(id)) },
                )
            }
            composable(Screen.Map.route) {
                MapScreen(
                    viewModel   = viewModel,
                    onMemyClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(viewModel = viewModel)
            }

            // ── Sub-screens ───────────────────────────────────────────────
            composable(Screen.Capture.route) {
                CaptureScreen(
                    viewModel = viewModel,
                    onSave    = { navController.navigateUp() },
                    onClose   = { navController.navigateUp() },
                )
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val memyId = backStackEntry.arguments?.getString("memyId") ?: return@composable
                DetailScreen(
                    memyId    = memyId,
                    viewModel = viewModel,
                    onBack    = { navController.navigateUp() },
                )
            }
            composable(Screen.Album.route) { backStackEntry ->
                val colId = backStackEntry.arguments?.getString("collectionId") ?: return@composable
                AlbumScreen(
                    collectionId = colId,
                    viewModel    = viewModel,
                    onBack       = { navController.navigateUp() },
                    onMemyClick  = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(
                    viewModel   = viewModel,
                    onBack      = { navController.navigateUp() },
                    onMemyClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                )
            }
        }
    }
}

// ── Bottom Navigation Bar ─────────────────────────────────────────────────────
@Composable
private fun MemyBottomBar(
    currentRoute: String?,
    onNavigate: (Screen) -> Unit,
    onFabClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .shadow(4.dp)
            .background(SurfaceCard),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            bottomNavItems.forEachIndexed { index, item ->
                // Insert FAB space in the center (index 2)
                if (index == 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
                BottomNavItem(
                    item     = item,
                    selected = currentRoute == item.screen.route,
                    onClick  = { onNavigate(item.screen) },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        // Center FAB
        CoralFAB(
            onClick  = onFabClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-16).dp),
        )
    }
}

@Composable
private fun BottomNavItem(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconColor = if (selected) Brand else TextMuted
    val icon      = if (selected) item.selectedIcon else item.unselectedIcon

    Column(
        modifier              = modifier
            .fillMaxHeight()
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp),
        horizontalAlignment   = Alignment.CenterHorizontally,
        verticalArrangement   = Arrangement.Center,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier         = if (selected)
                Modifier
                    .background(BrandWash, RoundedCornerShape(999.dp))
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            else Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        ) {
            Icon(icon, item.label, tint = iconColor, modifier = Modifier.size(22.dp))
        }
        Text(
            item.label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color      = if (selected) TextStrong else TextMuted,
                fontSize   = 11.sp,
            )
        )
    }
}

import androidx.compose.foundation.clickable
