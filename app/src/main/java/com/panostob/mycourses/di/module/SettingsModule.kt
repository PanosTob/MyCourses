package com.panostob.mycourses.di.module

import com.panostob.mycourses.data.settings.datasource.SettingsDataSource
import com.panostob.mycourses.data.settings.repository.SettingsRepositoryImpl
import com.panostob.mycourses.domain.settings.repository.SettingsRepository
import com.panostob.mycourses.framework.settings.datasource.SettingsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface SettingsBindsModule {

    @Binds
    fun bindSettingsRepositoryImpl(repository: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun bindSettingsDataSourceImpl(dataSource: SettingsDataSourceImpl): SettingsDataSource
}