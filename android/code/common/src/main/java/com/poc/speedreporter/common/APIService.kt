package com.poc.speedreporter.common

import com.poc.speedreporter.common.data.Speed
import com.poc.speedreporter.common.data.SpeedViolation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @POST("/setSpeedThreshold") //Need to check in mobile module
    fun setSpeedThreshold(@Body speedThreshold: Speed): Call<Speed>

    @GET("/getSpeedThreshold/{vin}") //Done
    fun getSpeedThreshold(@Path("vin") vin: String): Call<Speed>

    @POST("/reportSpeedViolation") //Done
    fun reportSpeedViolation(@Body speedViolation: SpeedViolation): Call<SpeedViolation>

    @POST("/getSpeedViolation/{vin}") //Need to check in mobile module
    fun getSpeedViolation(@Path("vin") vin: String): Call<List<SpeedViolation>>

    @GET("/clearData")
    suspend fun clearData(@Query("vin") vin: String): Any
}