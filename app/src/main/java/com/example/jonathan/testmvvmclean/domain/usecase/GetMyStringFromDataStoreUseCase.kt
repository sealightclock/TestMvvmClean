package com.example.jonathan.testmvvmclean.domain.usecase

import com.example.jonathan.testmvvmclean.data.local.MyStringDataStoreRepository
import kotlinx.coroutines.flow.Flow

class GetMyStringFromDataStoreUseCase(private val repository: MyStringDataStoreRepository) {
    fun execute(): Flow<String> {
        return repository.myStringFlow
    }
}
