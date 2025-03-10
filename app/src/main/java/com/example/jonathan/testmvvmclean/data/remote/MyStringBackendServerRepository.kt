package com.example.jonathan.testmvvmclean.data.remote

import kotlinx.coroutines.delay
import com.example.jonathan.testmvvmclean.domain.model.MyStringModel

class MyStringBackendServerRepository {
    suspend fun getMyString(): MyStringModel {
        delay(2000) // Simulate network delay
        return MyStringModel("Default Value from Server")
    }
}
