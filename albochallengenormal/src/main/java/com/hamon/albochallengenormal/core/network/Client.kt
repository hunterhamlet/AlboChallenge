package com.hamon.albochallengenormal.core.network

import com.google.gson.GsonBuilder
import com.hamon.albochallengenormal.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

    // Type clients
    const val DEFERRED = "deferred"

    private val okHttpClientInterceptorRequest by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(okHttpClientInterceptorRequest)
            .build()
    }

    fun getClient(host: String = BuildConfig.BASE_URL): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(host).build()
    }

    fun getClientDeferred(host: String = BuildConfig.BASE_URL): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(host).build()
    }

    inline fun <reified T> Retrofit.getServices(): T {
        return this.create(T::class.java)
    }
}