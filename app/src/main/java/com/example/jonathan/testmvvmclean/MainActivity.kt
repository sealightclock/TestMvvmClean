package com.example.jonathan.testmvvmclean

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jonathan.testmvvmclean.presentation.view.TestMvvmCleanApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestMvvmCleanApp() // ✅ ViewModel is now managed inside this Composable
        }
    }
}
