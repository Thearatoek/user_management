package com.app.user_mangement.ui.screen.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.klakmoum.R
import com.app.klakmoum.navigation.NavDirections
import com.app.user_mangement.ui.screen.auth.login.components.CustomTextField
import com.app.user_mangement.ui.widget.CustomButton
import com.app.user_mangement.ui.widget.CustomSocialButton
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.example.user.management.sample.data.model.LoginUiState

@Composable
fun RegisterScreen(
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
            color = Color.White,
            darkIcons =true
        )
        if (state is LoginUiState.Success) {
            navController.navigate("username_screen") {
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
                Spacer(Modifier.height(120.dp))
                Text(
                        text = "Register on Aepod",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Create an Aepod account, We can’t wait to have you.",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(Modifier.height(48.dp))

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(id =R.string.email),
                    hint = "",
                    isPassword = false,
                    leadingIcon = R.drawable.mail,
                    keyboardType = KeyboardType.Password
                )
                Spacer(Modifier.height(32.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(id =R.string.password),
                    hint = "",
                    isPassword = true,
                    leadingIcon = R.drawable.key,
                    keyboardType = KeyboardType.Password
                )

                state.takeIf { it is LoginUiState.Error }?.let {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = (it as LoginUiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    )
                }
                Spacer(Modifier.height(48.dp))
                Text(
                    text = "Or Login using social media",
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
              Column (
                  horizontalAlignment = Alignment.CenterHorizontally
              ){
                  Spacer(modifier = Modifier.weight(1f))
                  CustomButton(text = "Register",
                      onClick = {
                          viewModel.signUpUserWithEmailAndPassword(
                              email = email,
                              password = password,
                              username =""
                          )
                      },
                      isRegister = true)
                  Spacer(modifier = Modifier.height(16.dp))
                  Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                navController.navigate("login_screen") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                      text = "Already have an account? Login",
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

