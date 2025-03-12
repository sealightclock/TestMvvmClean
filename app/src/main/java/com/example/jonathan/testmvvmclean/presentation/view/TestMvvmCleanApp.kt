package com.example.jonathan.testmvvmclean.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jonathan.testmvvmclean.presentation.viewmodel.MyStringViewModel

@Composable
fun TestMvvmCleanApp(viewModel: MyStringViewModel) {
    val uiState by viewModel.myString.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "My String", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.value, // ✅ Using MyStringModel
            onValueChange = { viewModel.saveMyString(it) },
            label = { Text("Enter a value") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Disable input while loading
        )
        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Show progress indicator when loading
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
        }

        Button(
            onClick = { viewModel.updateFromServer() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Disable button while loading
        ) {
            Text(if (isLoading) "Updating..." else "Update from Server")
        }
    }
}
