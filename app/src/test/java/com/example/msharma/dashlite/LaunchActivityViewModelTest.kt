package com.example.msharma.dashlite

import android.app.Application
import com.example.msharma.dashlite.data.Address
import com.example.msharma.dashlite.data.LatLong
import com.example.msharma.dashlite.data.Restaurant
import com.example.msharma.dashlite.network.RestaurantService
import com.example.msharma.dashlite.util.MockHelper.any
import com.example.msharma.dashlite.util.RxJavaTestRunner
import com.example.msharma.dashlite.viewmodel.LaunchActivityViewModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.ArrayList


@RunWith(RxJavaTestRunner::class)
class LaunchActivityViewModelTest {

    private val mockApplication = mock(Application::class.java)
    private val mockRestaurantService = mock(RestaurantService::class.java)

    @Test
    @Throws(Exception::class)
    fun get_restaurant_test() {
        val list = ArrayList<Restaurant>()
        list.add(Restaurant("", "", "", "", "", "", 0.0f, Address("")))

        Assert.assertNotNull(mockRestaurantService)
        val launchActivityViewModel = LaunchActivityViewModel(mockApplication, mockRestaurantService)
        val testSubscriber = TestObserver<Boolean>()
        val testRestaurantList = TestObserver<List<Restaurant>>()
        launchActivityViewModel.showLoading.subscribe(testSubscriber)
        launchActivityViewModel.restaurantList.subscribe(testRestaurantList)
        `when`(mockRestaurantService.getNearByRestaurant(any<LatLong>())).thenReturn(Single.just(list))

        launchActivityViewModel.getRestaurants()
        testSubscriber.assertValueCount(2)
        testSubscriber.assertValues(true, false)
        testRestaurantList.assertValueCount(1)
        Assert.assertEquals(testRestaurantList.values()[0].size, list.size)
    }

    @Test
    @Throws(Exception::class)
    fun get_empty_restaurant_list_test() {
        val list = ArrayList<Restaurant>()

        val launchActivityViewModel = LaunchActivityViewModel(mockApplication, mockRestaurantService)
        val testSubscriber = TestObserver<Boolean>()
        val testEmptyListSubscriber = TestObserver<Unit>()
        val testRestaurantList = TestObserver<List<Restaurant>>()
        launchActivityViewModel.showLoading.subscribe(testSubscriber)
        launchActivityViewModel.restaurantList.subscribe(testRestaurantList)
        launchActivityViewModel.emptyList.subscribe(testEmptyListSubscriber)
        `when`(mockRestaurantService.getNearByRestaurant(any<LatLong>())).thenReturn(Single.just(list))

        launchActivityViewModel.getRestaurants()
        testSubscriber.assertValueCount(2)
        testSubscriber.assertValues(true, false)
        testRestaurantList.assertValueCount(0)
        testEmptyListSubscriber.assertValueCount(1)


    }

}