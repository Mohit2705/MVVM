package com.example.msharma.dashlite.viewmodelfactory

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.msharma.dashlite.network.RestaurantService
import com.example.msharma.dashlite.viewmodel.LaunchActivityViewModel

class LaunchViewModelFactory(private val application: Application, private val restaurantService: RestaurantService) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return LaunchActivityViewModel(application, restaurantService) as T
    }

}
