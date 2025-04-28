package com.st991719830.assignment2_adegokeakanbi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.st991719830.assignment2_adegokeakanbi.viewmodel.AuthViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(navController: NavController, viewModel: AuthViewModel, onClose: () -> Unit) {
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Menu", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        DrawerItem("Feed") {
            navController.navigate("feed")
            onClose()
        }

        DrawerItem("Profile") {
            navController.navigate("profile")
            onClose()
        }

        DrawerItem("Logout") {
            scope.launch {
                viewModel.logout()
                navController.navigate("login") {
                    popUpTo("feed") { inclusive = true }
                }
                onClose()
            }
        }
    }
}

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}
