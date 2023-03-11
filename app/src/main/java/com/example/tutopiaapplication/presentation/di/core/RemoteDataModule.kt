package com.example.tutopiaapplication.presentation.di.core

import com.example.tutopiaapplication.data.api.ApiVersion1Service
import com.example.tutopiaapplication.data.api.ApiVersion2Service
import com.example.tutopiaapplication.data.repository.datasource.RegisterDataSource
import com.example.tutopiaapplication.data.repository.datasource.RequestOtpDataSource
import com.example.tutopiaapplication.data.repository.datasource.UpdateProfileDataSource
import com.example.tutopiaapplication.data.repository.datasource.VerifyOtpDataSource
import com.example.tutopiaapplication.data.repository.datasourceImpl.RegisterDataSourceImpl
import com.example.tutopiaapplication.data.repository.datasourceImpl.RequestOtpDataSourceImpl
import com.example.tutopiaapplication.data.repository.datasourceImpl.UpdateProfileDataSourceImpl
import com.example.tutopiaapplication.data.repository.datasourceImpl.VerifyOtpDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule() {
/*
    @Singleton
    @Provides
    @Named("apiKey")
    fun provideApiKey() : String = BuildConfig.API_KEY*/

    @Singleton
    @Provides
    fun provideRegisterDataSource(apiVersion1Service: ApiVersion1Service, apiVersion2Service: ApiVersion2Service): RegisterDataSource {
        return RegisterDataSourceImpl(apiVersion2Service, apiVersion1Service)
    }

    @Singleton
    @Provides
    fun provideVerifyOtpDataSource(apiVersion1Service: ApiVersion1Service, apiVersion2Service: ApiVersion2Service): VerifyOtpDataSource {
        return VerifyOtpDataSourceImpl(apiVersion2Service, apiVersion1Service)
    }

    @Singleton
    @Provides
    fun provideRequestOtpDataSource(apiVersion2Service: ApiVersion2Service):RequestOtpDataSource {
        return RequestOtpDataSourceImpl(apiVersion2Service)
    }

    @Singleton
    @Provides
    fun provideUpdateProfileDataSource(apiVersion1Service: ApiVersion1Service, apiVersion2Service: ApiVersion2Service): UpdateProfileDataSource {
        return UpdateProfileDataSourceImpl(apiVersion2Service, apiVersion1Service)
    }
}