package com.app.klakmoum.ui.screen.user.user_update


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.example.user.management.sample.data.model.UserModel
import org.example.user.management.sample.ui.screen.user.user_list.UserViewModel


@Composable
fun UpdateUserScreen(
    userId: Int,
    viewModel: UserViewModel = hiltViewModel(),
    navController: NavController
) {
    // Collect user from Flow/StateFlow exposed by ViewModel
    val userState: UserModel? by viewModel.getUserById(userId).collectAsState(initial = null)
    val user = userState // Store in a local variable to avoid smart cast issues

    if (user == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("User not found", color = Color.Red)
        }
        return
    }

    // Remember and save input states, reset on user change
    var name by rememberSaveable { mutableStateOf(user.name) }
    var email by rememberSaveable { mutableStateOf(user.email) }
    var phone by rememberSaveable { mutableStateOf(user.phone) }
    var age by rememberSaveable { mutableStateOf(user.age.toString()) }
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier.fillMaxSize()
            .clickable {
            focusManager.clearFocus()
            },
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFFFFFFFF),
                title = { Text("Update User") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UserInputField(value = name, label = "Name") { name = it }
            UserInputField(value = email, label = "Email") { email = it }
            UserInputField(value = phone, label = "Phone") { phone = it }
            UserInputField(
                value = age,
                label = "Age",
                keyboardType = KeyboardType.Number
            ) { age = it }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val updatedUser = user.copy(
                        name = name,
                        email = email,
                        phone = phone,
                        age = age.toIntOrNull() ?: user.age
                    )
                    viewModel.updateUser(updatedUser)
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth()

            ) {
                Text("Update")
            }
        }
    }
}

@Composable
fun UserInputField(
    value: String,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}
