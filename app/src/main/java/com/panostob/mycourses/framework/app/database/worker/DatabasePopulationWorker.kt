package com.panostob.mycourses.framework.app.database.worker

import android.content.Context
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.panostob.mycourses.data.courses.model.MyCourseEntity
import com.panostob.mycourses.framework.courses.dao.CoursesDao
import com.panostob.mycourses.usecase.courses.GetAllCourses
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

@HiltWorker
class CoursesDatabasePopulationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val coursesDao: CoursesDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val existingCourses = coursesDao.getAllCourses().first()
            if (existingCourses.isNotEmpty()) {
                Timber.Forest.tag(CoursesDatabasePopulationWorker::class.java.simpleName).d("Database already populated with ${existingCourses.size} courses. Skipping population.")
            } else {
                Timber.Forest.tag(CoursesDatabasePopulationWorker::class.java.simpleName).d("Database is empty. Populating initial courses.")

                myCoursesListCache.forEach { course ->
                    coursesDao.upsertCourse(course)
                    Timber.Forest.tag(CoursesDatabasePopulationWorker::class.java.simpleName).d("Populate initial course: ${course.title}")
                }
                Timber.Forest.tag(CoursesDatabasePopulationWorker::class.java.simpleName).d("Database population complete.")
            }
            Result.success()
        } catch (ex: Exception) {
            Timber.Forest.tag(CoursesDatabasePopulationWorker::class.java.simpleName).e(ex)
            Result.failure()
        }
    }

    companion object {
        val myCoursesListCache = listOf(
            MyCourseEntity(
                id = 1,
                imageUrl = "/images/product/ef-light/learning-course-about-page.png",
                title = "Android Development",
                shortDescription = LoremIpsum(50).values.joinToString(" "),
                progressPercentage = 0f
            ),
            MyCourseEntity(
                id = 2,
                imageUrl = "/images/product/ef-light/learning-course-about-page.png",
                title = "Kotlin Language",
                shortDescription = LoremIpsum(300).values.joinToString(" "),
                progressPercentage = 0f
            ),
            MyCourseEntity(
                id = 3,
                imageUrl = "/images/product/ef-light/learning-course-about-page.png",
                title = "Jetpack Compose Guidelines",
                shortDescription = LoremIpsum(300).values.joinToString(" "),
                progressPercentage = 0f
            ),
            MyCourseEntity(
                id = 4,
                imageUrl = "/images/product/ef-light/learning-course-about-page.png",
                title = "User Experience Design",
                shortDescription = LoremIpsum(5).values.joinToString(" "),
                progressPercentage = 0f
            ),
            MyCourseEntity(
                id = 5,
                imageUrl = "/images/product/ef-light/learning-course-about-page.png",
                title = "Clean Architecture Course",
                shortDescription = LoremIpsum(5).values.joinToString(" "),
                progressPercentage = 0f
            ),
            MyCourseEntity(
                id = 6,
                imageUrl = "/images/product/ef-light/learning-course-about-page.png",
                title = "Model-View-ViewModel Course",
                shortDescription = LoremIpsum(5).values.joinToString(" "),
                progressPercentage = 0f
            ),
            MyCourseEntity(
                id = 7,
                imageUrl = "/images/product/ef-light/learning-course-about-page.png",
                title = "S.O.L.I.D Principle Course",
                shortDescription = LoremIpsum(5).values.joinToString(" "),
                progressPercentage = 0f
            ),
            MyCourseEntity(
                id = 8,
                imageUrl = "/images/product/ef-light/learning-course-about-page.png",
                title = "Unit Testing",
                shortDescription = LoremIpsum(5).values.joinToString(" "),
                progressPercentage = 0f
            ),
            MyCourseEntity(
                id = 9,
                imageUrl = "/images/product/ef-light/learning-course-about-page.png",
                title = "Test Driven Development",
                shortDescription = LoremIpsum(5).values.joinToString(" "),
                progressPercentage = 0f
            ),
        )
    }
}