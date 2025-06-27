package com.panostob.mycourses.ui.app

import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.panostob.mycourses.ui.app.AppViewModel
import kotlin.getValue

@AndroidEntryPoint
class AppActivity: ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()
}