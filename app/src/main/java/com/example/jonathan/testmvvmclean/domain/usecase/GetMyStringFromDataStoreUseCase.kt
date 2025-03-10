package com.example.jonathan.testmvvmclean.domain.usecase

import com.example.jonathan.testmvvmclean.data.local.MyStringDataStoreRepository
import com.example.jonathan.testmvvmclean.domain.model.MyStringModel

class GetMyStringFromDataStoreUseCase(private val repository: MyStringDataStoreRepository) {
    suspend fun execute(): MyStringModel {
        return MyStringModel(repository.getMyString()) // Now references getMyString()
    }
}
