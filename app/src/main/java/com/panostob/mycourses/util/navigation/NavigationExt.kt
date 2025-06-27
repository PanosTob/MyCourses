package com.panostob.mycourses.util.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber
import kotlin.reflect.KType
import kotlin.toString

internal fun NavController.safeNavigate(destination: NavigationDestination, popUpToRoute: NavigationDestination? = null) {
    try {
        navigate(route = destination) {
            launchSingleTop = true
            restoreState = true
            popUpToRoute?.let {
                popUpTo(it) {
                    inclusive = true
                    saveState = true
                }
            }
        }
        Timber.tag("safeNavigate").v(destination.toString())
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(e)
        Timber.tag("safeNavigate").e(e)
    }
}

inline fun <reified T : Any> NavGraphBuilder.slideInOutComposable(
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline sizeTransform: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? = null,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        typeMap = typeMap,
        deepLinks = deepLinks,
        enterTransition = { slideInHorizontally { offset -> offset } },
        popExitTransition = { slideOutHorizontally { offset -> offset } },
        exitTransition = { slideOutHorizontally { offset -> -offset } },
        popEnterTransition = { slideInHorizontally { offset -> -offset } },
        sizeTransform = sizeTransform,
        content = content
    )
}