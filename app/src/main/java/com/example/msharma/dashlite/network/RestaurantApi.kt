package com.example.msharma.dashlite.network

import com.example.msharma.dashlite.data.Restaurant
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RestaurantApi {
    @GET("v2/restaurant/")
    fun findNearByRestaurant(@Query("lat") latitude: String,
                             @Query("lng") longitude: String)
            : Single<List<Restaurant>>

    @GET("v2/restaurant/{id}")
    fun restaurantDetail(@Path("id") id: String)
            : Single<Restaurant>

}