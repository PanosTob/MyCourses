package com.panostob.mycourses.util.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
open class NavigationDestination {
    @Transient
    open val builder: NavOptionsBuilder.() -> Unit = { launchSingleTop = true }
}