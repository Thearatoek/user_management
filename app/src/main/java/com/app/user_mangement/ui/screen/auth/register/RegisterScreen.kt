package com.app.user_mangement.ui.screen.auth.register
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.LockOpen
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.app.klakmoum.R
import com.app.user_mangement.ui.screen.auth.login.LoginViewModel
import com.app.user_mangement.ui.screen.auth.login.components.CustomTextField
import com.app.user_mangement.ui.screen.auth.login.components.HeaderSection
import com.app.user_mangement.ui.screen.auth.login.components.LoginButton
import com.app.user_mangement.ui.screen.auth.login.components.SignUpPrompt
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import org.example.user.management.sample.data.model.LoginUiState


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel.loginState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(state) {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
        if (state is LoginUiState.Success) {
            navController.navigate("login_screen") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .background(color = Color.White)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            },
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                       navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Black
                        )
                    }
                }
            )
        },
    ) {paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
            ) {
              HeaderSection(
                  title = stringResource(id =R.string.sign_to_acc),
                  subtitle = stringResource(id =R.string.create_account),
                  )
                Spacer(modifier = Modifier.height(25.dp))
                CustomTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = stringResource(id =R.string.username),
                    hint = "",
                    isPassword = false,
                    leadingIcon = Icons.Default.Person,
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.height(25.dp))
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(id =R.string.email),
                    hint = "",
                    isPassword = false,
                    leadingIcon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(id =R.string.password),
                    hint = "",
                    isPassword = true,
                    leadingIcon = Icons.Rounded.LockOpen,
                    keyboardType = KeyboardType.Password
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = stringResource(id =R.string.forgot_password), color = MaterialTheme.colorScheme.primary, style = TextStyle(
                        fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight(600)
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                LoginButton(state, coroutineScope, stringResource(id =R.string.sign_up)) {
                    viewModel.signUpUserWithEmailAndPassword(email, password, userName)
                }
                state.takeIf { it is LoginUiState.Error }?.let {
                    Spacer(Modifier.height(16.dp))
                    androidx.compose.material3.Text(
                        text = (it as LoginUiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                SignUpPrompt(stringResource(id =R.string.have_acccount_yet),  stringResource(id =R.string.login), onClick = {
                    navController.navigate("login_screen")
                })
                Spacer(modifier =Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 16.dp), // Optional: space above and below
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = stringResource(id =R.string.by_click_register), color = Color.Black, style = TextStyle(
                                fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight(600)
                            )
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = stringResource(id =R.string.term_and_condition), color = MaterialTheme.colorScheme.secondaryContainer, style = TextStyle(
                                fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight(600)
                            )
                        )

                    }
                }

            }

        }

    }
}
