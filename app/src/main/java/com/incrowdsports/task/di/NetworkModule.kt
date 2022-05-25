package com.incrowdsports.task.di

import com.incrowdsports.task.model.api.FixtureService
import com.incrowdsports.task.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Singleton
	@Provides
	fun providesRetrofit(
		okHttpClient: OkHttpClient,
		gsonConverterFactory: GsonConverterFactory
	): Retrofit = Retrofit.Builder()
		.baseUrl(Constants.BASE_URL)
		.client(okHttpClient)
		.addConverterFactory(gsonConverterFactory)
		.build()

	@Singleton
	@Provides
	fun providesOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()

	@Singleton
	@Provides
	fun providesAPIService(retrofit: Retrofit): FixtureService = retrofit.create(FixtureService::class.java)

	@Provides
	@Singleton
	fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()
}
