package com.example.jonathan.testmvvmclean.domain.usecase

import com.example.jonathan.testmvvmclean.data.local.MyStringDataStoreRepository

class SaveMyStringToDataStoreUseCase(private val repository: MyStringDataStoreRepository) {
    suspend fun execute(newValue: String) {
        repository.saveMyString(newValue)
    }
}
