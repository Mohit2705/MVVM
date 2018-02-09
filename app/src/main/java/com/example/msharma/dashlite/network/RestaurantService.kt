package com.example.msharma.dashlite.network

import com.example.msharma.dashlite.data.LatLong
import com.example.msharma.dashlite.data.Restaurant
import io.reactivex.Single

// TODO for simplicity of project kept one service otherwise we can create 2 different one for list and another for detail
class RestaurantService(private val restaurantApi: RestaurantApi) {

    //TODO use argument values and remove hard coded values
    fun getNearByRestaurant(latLong: LatLong): Single<List<Restaurant>> {
        // TODO handle error like unable to reach host and have some sort of retry logic based on error
        return restaurantApi.findNearByRestaurant("37.422740", "-122.139956")
    }

    fun getRestaurantDetail(id: String): Single<Restaurant> {
        return restaurantApi.restaurantDetail(id)
    }

}