package com.st991719830.assignment2_adegokeakanbi.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.st991719830.assignment2_adegokeakanbi.viewmodel.AuthViewModel

@Composable
fun SignupScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Sign Up", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        OutlinedTextField(value = confirm, onValueChange = { confirm = it }, label = { Text("Confirm Password") }, visualTransformation = PasswordVisualTransformation())
        Button(onClick = {
            if (password == confirm) {
                viewModel.signup(email, password, {
                    navController.navigate("feed") {
                        popUpTo("signup") { inclusive = true }
                    }
                }, { error -> Log.e("Signup", error) })
            }
        }) {
            Text("Sign Up")
        }
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Already have an account? Login")
        }
    }
}
