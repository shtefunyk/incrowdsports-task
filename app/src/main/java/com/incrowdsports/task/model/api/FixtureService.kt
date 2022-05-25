package com.incrowdsports.task.model.api

import com.incrowdsports.task.model.data.DetailInfo
import com.incrowdsports.task.model.data.Fixture
import com.incrowdsports.task.model.data.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixtureService {

    @GET("matches")
    suspend fun getFixtureList(@Query("compId") compId: Int, @Query("season") season: Int, @Query("size") size: Int)
        : NetworkResponse<List<Fixture>>

    @GET("matches/{id}")
    suspend fun getFixtureDetail(@Path("id") id: String)
        : DetailInfo

}