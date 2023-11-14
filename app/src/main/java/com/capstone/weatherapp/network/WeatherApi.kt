package com.capstone.weatherapp
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApi {
    @GET("data/2.5/group")
    suspend fun getCities(
        @Query("id") cities: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ) : Response<WeatherApiResponse>

    @GET("data/2.5/weather")
    suspend fun getCity(
        @Query("id") cityId: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ) : Response<SingleCityResponse>
}

object WeatherApiClient {
    val retrofitService: WeatherApi by lazy { retrofit.create(WeatherApi::class.java) }
}