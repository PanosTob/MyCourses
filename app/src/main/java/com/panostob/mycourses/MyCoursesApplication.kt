package com.panostob.mycourses

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.panostob.mycourses.framework.app.database.worker.CoursesDatabasePopulationWorker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyCoursesApplication : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        scheduleCoursesDatabasePopulation()
    }

    private fun scheduleCoursesDatabasePopulation() {
        val workRequest = OneTimeWorkRequestBuilder<CoursesDatabasePopulationWorker>().build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "DatabasePopulationWork",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}