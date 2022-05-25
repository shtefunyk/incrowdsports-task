package com.incrowdsports.task.model.repository

import com.incrowdsports.task.model.api.FixtureService
import com.incrowdsports.task.model.data.Fixture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FixturesRepo @Inject constructor(
	private val apiService: FixtureService,
	private val ioScope: CoroutineScope
) {

	companion object {
		const val COMP_ID = 8
		const val SEASON = 2021
		const val SIZE = 10
	}

	suspend fun getFixturesList(): List<Fixture> {
		return withContext(ioScope.coroutineContext) {
			apiService.getFixtureList(COMP_ID, SEASON, SIZE).data
		}
	}
}
