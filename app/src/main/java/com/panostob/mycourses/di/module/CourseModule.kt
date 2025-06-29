package com.panostob.mycourses.di.module

import android.content.Context
import com.panostob.mycourses.data.courses.datasource.CoursesDataSource
import com.panostob.mycourses.data.courses.repository.CoursesRepositoryImpl
import com.panostob.mycourses.domain.courses.repository.CoursesRepository
import com.panostob.mycourses.framework.app.database.AppDatabase
import com.panostob.mycourses.framework.courses.dao.CoursesDao
import com.panostob.mycourses.framework.courses.datasource.CoursesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoursesModule {

    @Singleton
    @Provides
    fun provideCoursesDao(appDatabase: AppDatabase): CoursesDao {
        return appDatabase.coursesDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface CoursesBindsModule {

    @Binds
    fun bindCoursesRepositoryImpl(repository: CoursesRepositoryImpl): CoursesRepository

    @Binds
    fun bindCoursesDataSourceImpl(dataSource: CoursesDataSourceImpl): CoursesDataSource
}