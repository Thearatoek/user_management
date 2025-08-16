package com.app.user_mangement.ui.screen.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.klakmoum.R
import com.app.user_mangement.ui.screen.auth.login.components.CustomTextField
import com.app.user_mangement.ui.widget.CustomButton
import org.example.user.management.sample.data.model.LoginUiState

@Composable
fun PersonalizeScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){
    var username by remember { mutableStateOf("") }
    val state by viewModel.loginState.collectAsState()
    LaunchedEffect(state){
        if(state is LoginUiState.Success){
            navController.navigate(("login_screen"))
        }
    }
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xff0C9359), Color(0xff0EAD69))
                    )
                ),
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)

            ) {
                Spacer(Modifier.height(70.dp))
                Text(
                    text = "Let’s personalize your experience",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(Modifier.height(25.dp))
                Text(
                    text = "What can we call you? Could be your name, a nickname or something funny ☺.",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(Modifier.height(16.dp))
                CustomTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = "Name",
                    hint = "",
                    isPassword = false,
                    leadingIcon = R.drawable.person,
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier =Modifier.weight(1f))
                CustomButton(text = "Continue",
                    onClick = {
                       viewModel.updateUser(name = username)
                },
                    isRegister =true )
                Spacer(Modifier.height(25.dp))
            }
        }
    }
}