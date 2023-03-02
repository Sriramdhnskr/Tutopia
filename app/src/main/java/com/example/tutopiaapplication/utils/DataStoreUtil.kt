package com.example.tutopiaapplication.utils

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tutopiaapplication.utils.Constants.FIRST_LOGIN_COMPLETE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreUtil
@Inject
constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveFirstLoginComplete(value: Boolean = false){
        dataStore.edit {
            it[FIRST_LOGIN_COMPLETE] = value
        }
    }

    val firstLoginComplete: Flow<Boolean?>
        get() = dataStore.data.map {
            it[FIRST_LOGIN_COMPLETE]
        }

    suspend fun setData(prefKey: Preferences.Key<String>, value: String) {
        dataStore.edit {
            it[prefKey] = value
        }
    }

    fun getData(prefKey: Preferences.Key<String>, defaultValue: String? = null): Flow<String?> {
        return dataStore.data.map {
            it[prefKey]  ?: defaultValue
        }
    }

    suspend fun removeData(prefKey: Preferences.Key<String>) {
        dataStore.edit { it.remove(prefKey) }
    }


    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }
}