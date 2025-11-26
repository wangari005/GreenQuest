package com.greenquest.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val canSubmit = email.isNotBlank() && password.length >= 6 && !isLoading

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign in to GreenQuest", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(top = 8.dp)
        )

        if (error != null) {
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                if (!canSubmit) return@Button
                isLoading = true
                error = null
                val auth = FirebaseAuth.getInstance()
                scope.launch {
                    try {
                        auth.signInWithEmailAndPassword(email.trim(), password)
                            .await()
                        onLoginSuccess()
                    } catch (e: Exception) {
                        val msg = if (e is FirebaseAuthException) {
                            "${e.errorCode}: ${e.message}"
                        } else {
                            e.message
                        }
                        error = msg ?: "Login failed"
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = canSubmit,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(if (isLoading) "Signing in..." else "Log in")
        }

        Button(
            onClick = {
                if (!canSubmit) return@Button
                isLoading = true
                error = null
                val auth = FirebaseAuth.getInstance()
                scope.launch {
                    try {
                        val result = auth.createUserWithEmailAndPassword(email.trim(), password)
                            .await()
                        val user = result.user
                        if (user != null) {
                            val userRef = Firebase.database.reference.child("users").child(user.uid)
                            val newUser = hashMapOf(
                                "id" to user.uid,
                                "displayName" to (user.displayName ?: "Eco Warrior"),
                                "email" to user.email,
                                "totalPoints" to 0,
                                "level" to 1
                            )
                            userRef.setValue(newUser).await()
                        }
                        onLoginSuccess()
                    } catch (e: Exception) {
                        val msg = if (e is FirebaseAuthException) {
                            "${e.errorCode}: ${e.message}"
                        } else {
                            e.message
                        }
                        error = msg ?: "Sign up failed"
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = canSubmit,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(if (isLoading) "Creating account..." else "Sign up")
        }
    }
}
