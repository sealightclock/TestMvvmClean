package com.example.jonathan.testmvvmclean.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jonathan.testmvvmclean.domain.model.MyStringModel
import com.example.jonathan.testmvvmclean.domain.usecase.GetMyStringFromBackendServerUseCase
import com.example.jonathan.testmvvmclean.domain.usecase.GetMyStringFromDataStoreUseCase
import com.example.jonathan.testmvvmclean.domain.usecase.SaveMyStringToDataStoreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyStringViewModel(
    private val getMyStringFromDataStoreUseCase: GetMyStringFromDataStoreUseCase,
    private val saveMyStringToDataStoreUseCase: SaveMyStringToDataStoreUseCase,
    private val getMyStringFromBackendServerUseCase: GetMyStringFromBackendServerUseCase
) : ViewModel() {

    private val _myString = MutableStateFlow(MyStringModel(""))
    val myString = _myString.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _myString.value = getMyStringFromDataStoreUseCase.execute()
        }
    }

    fun saveMyString(newValue: String) {
        viewModelScope.launch {
            saveMyStringToDataStoreUseCase.execute(newValue)
            _myString.value = MyStringModel(newValue)
        }
    }

    fun updateFromServer() {
        viewModelScope.launch {
            _isLoading.value = true  // Show loading indicator
            _myString.value = getMyStringFromBackendServerUseCase.execute()
            _isLoading.value = false // Hide loading indicator
        }
    }
}
