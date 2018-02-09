package com.example.msharma.dashlite.activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.msharma.dashlite.MyApplication
import com.example.msharma.dashlite.R
import com.example.msharma.dashlite.SharedPrefernceHelper
import com.example.msharma.dashlite.adapters.RestaurantAdapter
import com.example.msharma.dashlite.network.RestaurantService
import com.example.msharma.dashlite.viewmodel.LaunchActivityViewModel
import com.example.msharma.dashlite.viewmodelfactory.LaunchViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_launch.progressbar
import kotlinx.android.synthetic.main.activity_launch.recyclerView
import javax.inject.Inject

private const val TAG = "LaunchActivity"
class LaunchActivity : AppCompatActivity() {

    private val bin: CompositeDisposable by lazy {
        CompositeDisposable()
    }


    private fun Disposable.into(bin: CompositeDisposable) {
        bin.add(this)
    }

    val sharedPrefernceHelper: SharedPrefernceHelper by lazy {
        SharedPrefernceHelper(applicationContext)
    }

    @Inject
    lateinit var restaurantService: RestaurantService

    private val viewModel: LaunchActivityViewModel by lazy {
        val viewModel = ViewModelProviders.of(this, LaunchViewModelFactory(application, restaurantService))
                .get(LaunchActivityViewModel::class.java)
        viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        //TODO replace with Android Injector
        (applicationContext as MyApplication).networkComponent.inject(this)

        val adapter = RestaurantAdapter(sharedPrefernceHelper)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, OrientationHelper.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter

        viewModel.showLoading.subscribe { isVisible ->
            progressbar.visibility = if (isVisible) View.VISIBLE else View.GONE
        }.into(bin)

        viewModel.emptyList.subscribe {
            Toast.makeText(this, getString(R.string.no_restaurant_msg), Toast.LENGTH_SHORT).show()
        }.into(bin)

        viewModel.restaurantList.subscribe { restaurantList ->
            Log.v(TAG, "list of restaurant is ${restaurantList.size}")
            adapter.setItems(restaurantList)
        }.into(bin)
        viewModel.getRestaurants()
    }

    override fun onDestroy() {
        super.onDestroy()
        bin.dispose()
    }
}
