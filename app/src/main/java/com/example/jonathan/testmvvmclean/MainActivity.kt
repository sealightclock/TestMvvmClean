package com.example.jonathan.testmvvmclean

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jonathan.testmvvmclean.data.local.MyStringDataStoreRepository
import com.example.jonathan.testmvvmclean.data.remote.MyStringBackendServerRepository
import com.example.jonathan.testmvvmclean.domain.usecase.GetMyStringFromBackendServerUseCase
import com.example.jonathan.testmvvmclean.domain.usecase.GetMyStringFromDataStoreUseCase
import com.example.jonathan.testmvvmclean.domain.usecase.SaveMyStringToDataStoreUseCase
import com.example.jonathan.testmvvmclean.presentation.view.TestMvvmCleanApp
import com.example.jonathan.testmvvmclean.presentation.viewmodel.MyStringViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Manually provide dependencies
        val localRepository = MyStringDataStoreRepository(applicationContext)
        val remoteRepository = MyStringBackendServerRepository()

        val getMyStringFromDataStoreUseCase = GetMyStringFromDataStoreUseCase(localRepository)
        val saveMyStringToDataStoreUseCase = SaveMyStringToDataStoreUseCase(localRepository)
        val getMyStringFromBackendServerUseCase = GetMyStringFromBackendServerUseCase(remoteRepository)

        val viewModel = MyStringViewModel(
            getMyStringFromDataStoreUseCase,
            saveMyStringToDataStoreUseCase,
            getMyStringFromBackendServerUseCase
        )

        setContent {
            TestMvvmCleanApp(viewModel = viewModel) // ✅ Now correctly provides ViewModel
        }
    }
}
