// NavRoutes.kt
package com.greenquest.app.navigation

sealed class NavRoute(val route: String) {
    data object Splash : NavRoute("splash")
    data object Onboarding : NavRoute("onboarding")
    data object Login : NavRoute("login")
    data object Home : NavRoute("home")
    data object Actions : NavRoute("actions")
    data object ActionDetail : NavRoute("action_detail/{actionId}") {
        fun create(actionId: String) = "action_detail/$actionId"
    }
    data object Badges : NavRoute("badges")
    data object Challenges : NavRoute("challenges")
    data object CreateCleanupEvent : NavRoute("create_cleanup_event")
    data object Community : NavRoute("community")
    data object ReportIssue : NavRoute("report_issue")
}