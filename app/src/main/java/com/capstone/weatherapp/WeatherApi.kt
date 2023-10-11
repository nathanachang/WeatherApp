package com.capstone.weatherapp
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.openweathermap.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApi {
    @GET("data/2.5/group?id=4772354,5368361,5128581,4887398,4699066,5809844,5856195,5419384,5391959,4684888,5746545,4990729,4335045&appid=6849ae760f417fb8188f4bb7fd0d92fc&units=imperial.json")
    suspend fun getCities() : WeatherApiResponse
}

object WeatherApiClient {
    val retrofitService: WeatherApi by lazy { retrofit.create(WeatherApi::class.java) }
}