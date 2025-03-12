package com.example.jonathan.testmvvmclean.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jonathan.testmvvmclean.domain.model.MyStringModel
import com.example.jonathan.testmvvmclean.domain.usecase.GetMyStringFromDataStoreUseCase
import com.example.jonathan.testmvvmclean.domain.usecase.SaveMyStringToDataStoreUseCase
import com.example.jonathan.testmvvmclean.domain.usecase.GetMyStringFromBackendServerUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyStringViewModel(
    getMyStringFromDataStoreUseCase: GetMyStringFromDataStoreUseCase,
    private val saveMyStringToDataStoreUseCase: SaveMyStringToDataStoreUseCase,
    private val getMyStringFromBackendServerUseCase: GetMyStringFromBackendServerUseCase
) : ViewModel() {

    // ✅ Automatically fetch latest value from DataStore using Flow
    private val _myString = getMyStringFromDataStoreUseCase.execute()
        .map { MyStringModel(it) } // Convert String to MyStringModel
        .stateIn(viewModelScope, SharingStarted.Lazily, MyStringModel("Default Local Value"))

    val myString: StateFlow<MyStringModel> = _myString

    // ✅ Loading state for UI feedback
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ✅ Save new value and update DataStore
    fun saveMyString(newValue: String) {
        viewModelScope.launch {
            saveMyStringToDataStoreUseCase.execute(newValue)
        }
    }

    // ✅ Fetch from backend, update UI and persist in DataStore
    fun updateFromServer() {
        viewModelScope.launch {
            _isLoading.value = true  // Show loading indicator
            val newValue = getMyStringFromBackendServerUseCase.execute()
            saveMyStringToDataStoreUseCase.execute(newValue.value) // Persist in DataStore
            _isLoading.value = false // Hide loading indicator
        }
    }
}
