package com.example.msharma.dashlite.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.msharma.dashlite.data.LatLong
import com.example.msharma.dashlite.data.Restaurant
import com.example.msharma.dashlite.network.RestaurantService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.Exceptions
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

private const val TAG = "LaunchActivityViewModel"

// TODO instead of injecting service we should inject repository which will also implement  some sort of caching
// TODO  remove the app context from viewmodel by using simple "ViewModel"  and inject some sort of resource helper in case we need them
class LaunchActivityViewModel(application: Application, private val restaurantService: RestaurantService) : AndroidViewModel(application) {

    private val bin: CompositeDisposable by lazy {
        CompositeDisposable()
    }


    private fun Disposable.into(bin: CompositeDisposable) {
        bin.add(this)
    }

    val restaurantList: PublishSubject<List<Restaurant>> = PublishSubject.create()
    val showLoading: PublishSubject<Boolean> = PublishSubject.create()
    val emptyList: PublishSubject<Unit> = PublishSubject.create()
    //TODO though it not used now but we can use in case something is going wrong with servers or connection and inform users
    private val errorMsg: PublishSubject<String> = PublishSubject.create()

    fun getRestaurants() {
        showLoading.onNext(true)
        restaurantService.getNearByRestaurant(LatLong(0.0, 0.0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    showLoading.onNext(false)
                    if (list.isNotEmpty()) {
                        restaurantList.onNext(list)
                    } else {
                        emptyList.onNext(Unit)
                    }
                }, { error ->
                    // TODO handle error
                    throw Exceptions.propagate(Exception(error.message ?: ""))
                }).into(bin)

    }

    override fun onCleared() {
        super.onCleared()
        bin.dispose()
    }

}