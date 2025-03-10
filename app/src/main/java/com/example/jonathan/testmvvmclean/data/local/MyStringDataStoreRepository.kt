package com.example.jonathan.testmvvmclean.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "my_string_prefs")

class MyStringDataStoreRepository(private val appContext: Context) {

    private val myStringKey = stringPreferencesKey("my_string")

    suspend fun getMyString(): String {
        val preferences = appContext.dataStore.data.first()
        return preferences[myStringKey] ?: "Default Local Value"
    }

    suspend fun saveMyString(newValue: String) {
        appContext.dataStore.edit { preferences ->
            preferences[myStringKey] = newValue
        }
    }
}
