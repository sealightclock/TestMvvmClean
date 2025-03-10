package com.example.jonathan.testmvvmclean.domain.usecase

import com.example.jonathan.testmvvmclean.data.remote.MyStringBackendServerRepository
import com.example.jonathan.testmvvmclean.domain.model.MyStringModel

class GetMyStringFromBackendServerUseCase(private val repository: MyStringBackendServerRepository) {
    suspend fun execute(): MyStringModel {
        return repository.getMyString() // Now correctly returning MyStringModel
    }
}
