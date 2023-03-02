package com.example.tutopiaapplication.presentation.di.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.*
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Singleton
    @Provides
    fun dataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        preferencesDataStore(name = "tutopiaApp-datastore").getValue(context, String::javaClass)
}