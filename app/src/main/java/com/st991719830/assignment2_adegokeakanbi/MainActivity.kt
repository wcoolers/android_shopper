package com.st991719830.assignment2_adegokeakanbi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.st991719830.assignment2_adegokeakanbi.nav.AppNavHost
import com.st991719830.assignment2_adegokeakanbi.ui.theme.Assignment2_AdegokeAkanbiTheme
import com.st991719830.assignment2_adegokeakanbi.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            Assignment2_AdegokeAkanbiTheme {
                val navController = rememberNavController()
                val viewModel: AuthViewModel = viewModel()

                AppNavHost(navController, viewModel)
            }
        }
    }
}
