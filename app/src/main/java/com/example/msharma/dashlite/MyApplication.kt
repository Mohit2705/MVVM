package com.example.msharma.dashlite

import android.app.Application
import android.os.StrictMode
import com.example.msharma.dashlite.BuildConfig
import com.example.msharma.dashlite.dagger.DaggerNetworkComponent
import com.example.msharma.dashlite.dagger.NetworkComponent
import com.example.msharma.dashlite.dagger.NetworkModule

class MyApplication : Application() {

    val networkComponent: NetworkComponent by lazy {
        val component = DaggerNetworkComponent.builder()
                .networkModule(NetworkModule())
                .build()
        component
    }

    override fun onCreate() {
        // due to this https://github.com/square/okhttp/issues/3537  found way to enable strict mode as it is very helpfuly
        //  turnOnStrictMode()
        super.onCreate()
    }

    private fun turnOnStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    StrictMode.ThreadPolicy.Builder().detectAll()
                            .penaltyLog().penaltyDeath().build())
            StrictMode.setVmPolicy(
                    StrictMode.VmPolicy.Builder().detectAll()
                            .penaltyLog().penaltyDeath().build())
        }
    }
}
