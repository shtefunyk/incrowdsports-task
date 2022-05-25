package com.incrowdsports.task.model.repository

import com.incrowdsports.task.model.api.FixtureService
import com.incrowdsports.task.model.data.DetailInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FixtureDetailRepo @Inject constructor(
    private val apiService: FixtureService,
    private val ioScope: CoroutineScope
) {

    suspend fun getFixtureDetail(id: String): DetailInfo {
        return withContext(ioScope.coroutineContext) {
            apiService.getFixtureDetail(id)
        }
    }
}