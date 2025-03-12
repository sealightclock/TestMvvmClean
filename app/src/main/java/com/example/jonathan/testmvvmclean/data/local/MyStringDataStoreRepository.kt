package com.example.jonathan.testmvvmclean.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "my_string_prefs")

class MyStringDataStoreRepository(private val appContext: Context) {

    private val myStringKey = stringPreferencesKey("my_string")

    val myStringFlow: Flow<String> = appContext.dataStore.data
        .map { preferences -> preferences[myStringKey] ?: "Default Local Value" }

    suspend fun saveMyString(newValue: String) {
        appContext.dataStore.edit { preferences ->
            preferences[myStringKey] = newValue
        }
    }
}
