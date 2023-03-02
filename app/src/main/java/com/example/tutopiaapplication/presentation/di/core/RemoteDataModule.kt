package com.example.tutopiaapplication.presentation.di.core

import com.example.tutopiaapplication.data.api.ApiVersion1Service
import com.example.tutopiaapplication.data.api.ApiVersion2Service
import com.example.tutopiaapplication.data.repository.datasource.RegisterDataSource
import com.example.tutopiaapplication.data.repository.datasourceImpl.RegisterDataSourceImpl
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
}