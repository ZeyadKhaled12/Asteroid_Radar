package com.udacity.asteroidradar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class AsteroidRadarApiFilter(val value: String) { SHOW("saved"),
    SHOW_TODAY("today"), SHOW_WEEK("week") }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL).build()


interface AsteroidRadarServices{

    @GET(value = "neo/rest/v1/feed")
    fun getAsteroidAsync(@Query("api_key") apiKey: String) : Deferred<String>

    @GET(value = "planetary/apod")
    fun getPictureAsync(@Query("api_key") apiKey: String): Deferred<PictureOfDay>

}

object AsteroidRadarApi{
    val retrofitService: AsteroidRadarServices by lazy {
        retrofit.create(AsteroidRadarServices::class.java)
    }
}