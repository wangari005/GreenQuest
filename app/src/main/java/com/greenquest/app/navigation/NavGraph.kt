package com.greenquest.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.greenquest.app.ui.screens.*

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Splash.route
    ) {
        composable(NavRoute.Splash.route) {
            SplashScreen { done ->
                if (done) {
                    navController.navigate(NavRoute.Login.route) {
                        popUpTo(NavRoute.Splash.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(NavRoute.Onboarding.route) {
                        popUpTo(NavRoute.Splash.route) { inclusive = true }
                    }
                }
            }
        }
        composable(NavRoute.Onboarding.route) {
            OnboardingScreen(
                onContinue = {
                    navController.navigate(NavRoute.Login.route) {
                        popUpTo(NavRoute.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(NavRoute.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavRoute.Home.route) {
                        popUpTo(NavRoute.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(NavRoute.Home.route) {
            HomeScreen(
                onOpenActions = { navController.navigate(NavRoute.Actions.route) },
                onOpenBadges = { navController.navigate(NavRoute.Badges.route) },
                onOpenChallenges = { navController.navigate(NavRoute.Challenges.route) },
                onCreateCleanupEvent = { navController.navigate(NavRoute.CreateCleanupEvent.route) },
                onOpenCommunity = { navController.navigate(NavRoute.Community.route) }
            )
        }
        composable(NavRoute.Actions.route) {
            ActionsScreen(onOpenDetail = { id ->
                navController.navigate(NavRoute.ActionDetail.create(id))
            })
        }
        composable(
            route = NavRoute.ActionDetail.route,
            arguments = listOf(navArgument("actionId") { type = NavType.StringType })
        ) {
            val actionId = it.arguments?.getString("actionId").orEmpty()
            ActionDetailScreen(
                actionId = actionId,
                onBack = { navController.popBackStack() },
                onCompleted = { navController.popBackStack() }
            )
        }
        composable(NavRoute.Badges.route) {
            BadgesScreen(onBack = { navController.popBackStack() })
        }
        composable(NavRoute.Challenges.route) {
            ChallengesScreen(onBack = { navController.popBackStack() })
        }
        composable(NavRoute.CreateCleanupEvent.route) {
            CreateCleanupEventScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(NavRoute.Community.route) {
            CommunityScreen(
                onBack = { navController.popBackStack() },
                onReportIssue = { navController.navigate(NavRoute.ReportIssue.route) }
            )
        }
        composable(NavRoute.ReportIssue.route) {
            ReportIssueScreen(
                onBack = { navController.popBackStack() },
                onSubmit = { navController.popBackStack() }
            )
        }
    }
}