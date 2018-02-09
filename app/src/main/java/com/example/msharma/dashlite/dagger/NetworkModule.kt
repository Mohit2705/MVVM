package com.example.msharma.dashlite.dagger

import com.example.msharma.dashlite.BuildConfig
import com.example.msharma.dashlite.network.RestaurantApi
import com.example.msharma.dashlite.network.RestaurantService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val END_POINT = "api.doordash.com"

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseURL(): HttpUrl {
        return HttpUrl.Builder()
                .scheme("https")
                .host(END_POINT)
                .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor {
        val logLevel = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val loggingInterceptor = HttpLoggingInterceptor()
        return loggingInterceptor.setLevel(logLevel)

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetroFit(baseURL: HttpUrl, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun privateRestaurantAPI(retrofit: Retrofit): RestaurantApi {
        return retrofit.create(RestaurantApi::class.java)
    }

    @Provides
    @Singleton
    fun privateRestaurantService(api: RestaurantApi): RestaurantService {
        return RestaurantService(api)
    }

}
