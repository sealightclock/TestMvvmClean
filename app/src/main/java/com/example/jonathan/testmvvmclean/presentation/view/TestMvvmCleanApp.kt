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

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "My String", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.value, // ✅ Using MyStringModel
            onValueChange = { viewModel.saveMyString(it) },
            label = { Text("Enter a value") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.updateFromServer() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update from Server")
        }
    }
}
