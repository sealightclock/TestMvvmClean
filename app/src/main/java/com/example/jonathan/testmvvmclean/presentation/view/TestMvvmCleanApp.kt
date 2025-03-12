package com.example.jonathan.testmvvmclean.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jonathan.testmvvmclean.data.local.MyStringDataStoreRepository
import com.example.jonathan.testmvvmclean.data.remote.MyStringBackendServerRepository
import com.example.jonathan.testmvvmclean.domain.usecase.GetMyStringFromBackendServerUseCase
import com.example.jonathan.testmvvmclean.domain.usecase.GetMyStringFromDataStoreUseCase
import com.example.jonathan.testmvvmclean.domain.usecase.SaveMyStringToDataStoreUseCase
import com.example.jonathan.testmvvmclean.presentation.viewmodel.MyStringViewModel

@Composable
fun TestMvvmCleanApp() {
    val context = LocalContext.current.applicationContext

    // ✅ ViewModel is now created inside Compose using viewModel()
    val viewModel: MyStringViewModel = viewModel(
        factory = MyStringViewModel.Factory(
            getMyStringFromDataStoreUseCase = GetMyStringFromDataStoreUseCase(MyStringDataStoreRepository(context)),
            saveMyStringToDataStoreUseCase = SaveMyStringToDataStoreUseCase(MyStringDataStoreRepository(context)),
            getMyStringFromBackendServerUseCase = GetMyStringFromBackendServerUseCase(MyStringBackendServerRepository())
        )
    )

    val uiState by viewModel.myString.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "My String", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.value,
            onValueChange = { viewModel.saveMyString(it) },
            label = { Text("Enter a value") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
        }

        Button(
            onClick = { viewModel.updateFromServer() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Updating..." else "Update from Server")
        }
    }
}
