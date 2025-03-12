package com.example.jonathan.testmvvmclean.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    private val _myString = getMyStringFromDataStoreUseCase.execute()
        .map { MyStringModel(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, MyStringModel("Default Local Value"))

    val myString: StateFlow<MyStringModel> = _myString

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun saveMyString(newValue: String) {
        viewModelScope.launch {
            saveMyStringToDataStoreUseCase.execute(newValue)
        }
    }

    fun updateFromServer() {
        viewModelScope.launch {
            _isLoading.value = true
            val newValue = getMyStringFromBackendServerUseCase.execute()
            saveMyStringToDataStoreUseCase.execute(newValue.value) // Persist in DataStore
            _isLoading.value = false
        }
    }

    // ✅ ViewModel Factory to Inject Dependencies
    class Factory(
        private val getMyStringFromDataStoreUseCase: GetMyStringFromDataStoreUseCase,
        private val saveMyStringToDataStoreUseCase: SaveMyStringToDataStoreUseCase,
        private val getMyStringFromBackendServerUseCase: GetMyStringFromBackendServerUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyStringViewModel(
                getMyStringFromDataStoreUseCase,
                saveMyStringToDataStoreUseCase,
                getMyStringFromBackendServerUseCase
            ) as T
        }
    }
}
