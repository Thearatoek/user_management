package com.app.user_mangement.ui.screen.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.app.klakmoum.R
import com.app.user_mangement.ui.screen.auth.login.LoginUiState
import com.app.user_mangement.ui.screen.auth.login.LoginViewModel
import com.app.user_mangement.ui.screen.auth.login.components.CustomTextField
import com.app.user_mangement.ui.widget.CustomButton
import com.app.user_mangement.ui.widget.CustomSocialButton
import com.google.accompanist.systemuicontroller.rememberSystemUiController
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel.loginState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(state) {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
        if (state is LoginUiState.Success) {
            navController.navigate("dashboard") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { focusManager.clearFocus() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf( Color(0xff0EAD69),Color(0xff0C9359),)
                    )
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(Modifier.height(120.dp))
                androidx.compose.material3.Text(
                    text = "Welcome back",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(Modifier.height(16.dp))
                androidx.compose.material3.Text(
                    text = "Let’s get back to growing your Aepod plants, shall we?",
                    style =  MaterialTheme.typography.labelLarge.copy(
                        fontSize = 18.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(Modifier.height(48.dp))
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(id = R.string.email),
                    hint = "",
                    isPassword = false,
                    leadingIcon = R.drawable.mail,
                    keyboardType = KeyboardType.Password
                )
                Spacer(Modifier.height(16.dp))
                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(id = R.string.password),
                    hint = "",
                    isPassword = true,
                    leadingIcon = R.drawable.key,
                    keyboardType = KeyboardType.Password
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Forgot your password?",
                    style =  MaterialTheme.typography.labelLarge.copy(
                        fontSize = 18.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                state.takeIf { it is LoginUiState.Error }?.let {
                    Spacer(Modifier.height(16.dp))
                    androidx.compose.material3.Text(
                        text = (it as LoginUiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    )
                }
                Spacer(Modifier.height(48.dp))
                androidx.compose.material3.Text(
                    text = "Or Register using social media",
                    style =  MaterialTheme.typography.labelLarge.copy(
                        fontSize = 18.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomSocialButton(
                        icon = R.drawable.google_icon,
                        text = "Google",
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth() // important!
                    )
                    CustomSocialButton(
                        icon = R.drawable.facebook,
                        text = "Facebook",
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth() // important!
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    CustomButton(text = "Login",
                        onClick = {
                                  viewModel.loginWithEmailAndPassword(
                                      email = email,
                                      password = password
                                  )
                        },
                        isLoading = state,
                        isRegister = true)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                navController.navigate("register_screen") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                        text = "New here? Register",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700)
                        ),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
