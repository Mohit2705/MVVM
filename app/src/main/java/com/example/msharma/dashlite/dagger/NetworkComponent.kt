package com.example.msharma.dashlite.dagger

import com.example.msharma.dashlite.activities.DetailActivity
import com.example.msharma.dashlite.activities.LaunchActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(activity: LaunchActivity)
    fun inject(detailActivity: DetailActivity)
}
