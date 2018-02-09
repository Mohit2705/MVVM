package com.example.msharma.dashlite.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.msharma.dashlite.MyApplication
import com.example.msharma.dashlite.R
import com.example.msharma.dashlite.network.RestaurantService
import com.example.msharma.dashlite.viewmodel.DetailActivityViewModel
import com.example.msharma.dashlite.viewmodelfactory.DetailViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_launch.progressbar
import kotlinx.android.synthetic.main.restaurant_detail.address
import kotlinx.android.synthetic.main.restaurant_detail.description
import kotlinx.android.synthetic.main.restaurant_detail.imageView
import kotlinx.android.synthetic.main.restaurant_detail.name
import kotlinx.android.synthetic.main.restaurant_detail.ordertime
import kotlinx.android.synthetic.main.restaurant_detail.phoneNumber
import kotlinx.android.synthetic.main.restaurant_detail.ratingBar
import javax.inject.Inject

private const val RESTAURANT_ID = "RESTAURANT_ID"
private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {

    companion object {
        fun startDetailActivity(context: Context, id: String) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(RESTAURANT_ID, id)
            context.startActivity(intent)
        }

    }

    private val bin: CompositeDisposable by lazy {
        CompositeDisposable()
    }


    private fun Disposable.into(bin: CompositeDisposable) {
        bin.add(this)
    }

    @Inject
    lateinit var restaurantService: RestaurantService

    private val viewModel: DetailActivityViewModel by lazy {
        val viewModel = ViewModelProviders.of(this, DetailViewModelFactory(application, restaurantService))
                .get(DetailActivityViewModel::class.java)
        viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MyApplication).networkComponent.inject(this)
        setContentView(R.layout.restaurant_detail)
        Log.v(TAG, "CLicked id is ${intent.extras.getString(RESTAURANT_ID)}")
        // TODO handle when we reach this activity and don't have any id

        viewModel.showLoading.subscribe { isVisible ->
            progressbar.visibility = if (isVisible) View.VISIBLE else View.GONE
        }.into(bin)

        viewModel.restaurantDetail.subscribe { restaurantInfo ->
            Log.v(TAG, "Restaurant detail is $restaurantInfo")
            Glide.with(this).load(restaurantInfo.imageUrl).into(imageView)
            address.text = restaurantInfo.address.address
            name.text = restaurantInfo.name
            phoneNumber.text = restaurantInfo.phoneNumber
            description.text = restaurantInfo.description
            ratingBar.rating = restaurantInfo.rating
            ordertime.text = restaurantInfo.duration
        }.into(bin)
        viewModel.getRestaurantDetail(intent.extras.getString(RESTAURANT_ID))

    }

    override fun onDestroy() {
        super.onDestroy()
        bin.dispose()
    }
}