package com.incrowdsports.task.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesDispatchersModule {

	@Singleton
	@Provides
	fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
