package com.example.msharma.dashlite.viewmodelfactory

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.msharma.dashlite.network.RestaurantService
import com.example.msharma.dashlite.viewmodel.DetailActivityViewModel

class DetailViewModelFactory(private val application: Application, private val restaurantService: RestaurantService) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return DetailActivityViewModel(application, restaurantService) as T
    }

}
