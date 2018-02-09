package com.example.msharma.dashlite.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.msharma.dashlite.data.Restaurant
import com.example.msharma.dashlite.network.RestaurantService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.Exceptions
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

private const val TAG = "DetailActivityViewModel"

class DetailActivityViewModel(application: Application, private val restaurantService: RestaurantService) : AndroidViewModel(application) {

    private val bin: CompositeDisposable by lazy {
        CompositeDisposable()
    }


    private fun Disposable.into(bin: CompositeDisposable) {
        bin.add(this)
    }

    val restaurantDetail: PublishSubject<Restaurant> = PublishSubject.create()
    val showLoading: PublishSubject<Boolean> = PublishSubject.create()

    fun getRestaurantDetail(id: String) {
        showLoading.onNext(true)
        restaurantService.getRestaurantDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    showLoading.onNext(false)
                    restaurantDetail.onNext(list)
                }, { error ->
                    throw Exceptions.propagate(Exception(error.message ?: ""))
                }).into(bin)

    }

    override fun onCleared() {
        super.onCleared()
        bin.dispose()
    }

}