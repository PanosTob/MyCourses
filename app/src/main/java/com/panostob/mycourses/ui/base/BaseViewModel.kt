package com.panostob.mycourses.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    protected fun launch(updateLoading: (Boolean) -> Unit = {}, function: suspend () -> Unit): Job =
        viewModelScope.launch {
            updateLoading(true)
            function()
        }.also {
            it.invokeOnCompletion {
                updateLoading(false)
            }
        }

    protected fun launchOnIO(updateLoading: (Boolean) -> Unit = {}, function: suspend () -> Unit): Job =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateLoading(true)
                function()
            }
        }.also {
            it.invokeOnCompletion {
                updateLoading(false)
            }
        }
}
