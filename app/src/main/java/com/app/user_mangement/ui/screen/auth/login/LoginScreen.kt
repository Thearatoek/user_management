package com.app.user_mangement.ui.screen.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.LockOpen
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.klakmoum.R
import com.app.user_mangement.ui.screen.auth.login.components.CustomTextField
import com.app.user_mangement.ui.screen.auth.login.components.DividerWithText
import com.app.user_mangement.ui.screen.auth.login.components.HeaderSection
import com.app.user_mangement.ui.screen.auth.login.components.LoginButton
import com.app.user_mangement.ui.screen.auth.login.components.SignUpPrompt
import com.app.user_mangement.ui.screen.auth.login.components.SocialLoginButton
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.example.user.management.sample.data.model.LoginUiState

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel.loginState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(state) {
        systemUiController.setSystemBarsColor(
            color = Color(0xff317a59),
            darkIcons =false
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
                .background(Color.White)
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
            ) {
                HeaderSection(
                    title = stringResource(id =R.string.welcome_back),
                    subtitle =stringResource(id =R.string.sign_to_acc)
                )
                Spacer(Modifier.height(25.dp))

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(id =R.string.email),
                    hint = "",
                    isPassword = false,
                    leadingIcon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(16.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(id =R.string.password),
                    hint = "",
                    isPassword = true,
                    leadingIcon = Icons.Rounded.LockOpen,
                    keyboardType = KeyboardType.Password
                )
                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(id =R.string.forgot_password),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )
                Spacer(Modifier.height(24.dp))

                LoginButton(state, coroutineScope, stringResource(id =R.string.login)) {
                    viewModel.loginWithEmailAndPassword(email, password)
                }

                state.takeIf { it is LoginUiState.Error }?.let {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = (it as LoginUiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    )
                }

                Spacer(Modifier.height(24.dp))
                SignUpPrompt(stringResource(id =R.string.dont_have_account), stringResource(id =R.string.sign_up), onClick = {
                    navController.navigate("register_screen")
                })
                Spacer(Modifier.height(24.dp))
                DividerWithText("Or")

                Spacer(Modifier.height(24.dp))
                SocialLoginButton(R.drawable.google, stringResource(id =R.string.sign_in_with_google)) { /* TODO */ }
                Spacer(Modifier.height(16.dp))
                SocialLoginButton(R.drawable.mac, stringResource(id =R.string.sign_in_with_apple)) { /* TODO */ }
            }
        }
    }
}

