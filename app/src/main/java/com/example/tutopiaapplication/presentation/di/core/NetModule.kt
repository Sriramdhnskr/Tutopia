package com.example.tutopiaapplication.presentation.di.core

import android.util.Log
import com.example.tutopiaapplication.BuildConfig
import com.example.tutopiaapplication.data.api.ApiVersion1Service
import com.example.tutopiaapplication.data.api.ApiVersion2Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule() {

    @Singleton
    @Provides
    @Named("baseVersion1Url")
    fun provideBaseVersion1Url() : String = BuildConfig.BASE_VERSION1_URL

    @Singleton
    @Provides
    @Named("baseVersion2Url")
    fun provideBaseVersion2Url() : String = BuildConfig.BASE_VERSION2_URL

    @Singleton
    @Provides
    @Named("baseVersion1Retrofit")
    fun provideVersion1Retrofit(okHttpClient: OkHttpClient, @Named("baseVersion1Url") baseUrl : String): Retrofit{
        Log.i("base_url","Base_url ${baseUrl}")
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("baseVersion2Retrofit")
    fun provideVersion2Retrofit(okHttpClient: OkHttpClient, @Named("baseVersion2Url") baseUrl : String): Retrofit{
        Log.i("base_url","Base_url ${baseUrl}")
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiVersion1Service( @Named("baseVersion1Retrofit") retrofit: Retrofit) : ApiVersion1Service{
        return retrofit.create(ApiVersion1Service::class.java)
    }

    @Singleton
    @Provides
    fun provideApiVersion2Service( @Named("baseVersion2Retrofit") retrofit: Retrofit) : ApiVersion2Service{
        return retrofit.create(ApiVersion2Service::class.java)
    }


    @Singleton
    @Provides
    fun getInterceptedHttpClientBuilder(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)
          /*  .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)*/
        return httpClient.build()
    }

}