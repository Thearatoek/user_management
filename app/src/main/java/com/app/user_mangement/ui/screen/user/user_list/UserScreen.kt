package com.app.klakmoum.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ToggleOff
import androidx.compose.material.icons.outlined.ToggleOn
import androidx.compose.material3.rememberDrawerState // ✅ from material3
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.klakmoum.R
import com.app.user_mangement.core.NetworkMonitor
import com.app.user_mangement.core.NetworkStatus
import com.app.user_mangement.ui.screen.auth.login.LoginViewModel
import com.app.user_mangement.ui.screen.auth.login.components.LoginButton
import com.app.user_mangement.ui.screen.user.user_list.LanguageViewModel
import com.app.user_mangement.ui.screen.user.user_list.ThemeModeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import org.example.user.management.sample.data.model.LoginUiState

@Composable
fun UserScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel(),
    themeModeViewModel: ThemeModeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val systemUiController = rememberSystemUiController()
    val state by viewModel.loginState.collectAsState()
    val themeCode = themeModeViewModel.themeMode.collectAsState()
    val isConnected = remember { mutableStateOf(true) } // Replace with actual network check if needed
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (themeCode.value == "light") Color.White else Color(0xff0C0C27),
            darkIcons = themeCode.value == "light"
        )
        if (state is LoginUiState.Success) {
            navController.navigate("login_screen")
        }
    }
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
    )
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.background,
            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.setting),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    ThemeSwitcherScreen(viewModel = themeModeViewModel, languageViewModel = languageViewModel)
                    Spacer(modifier =Modifier.weight(1f))
                    LoginButton(state, scope, stringResource(id =R.string.logout)) {
                        viewModel.logout()
                        scope.launch {
                            drawerState.close()
                            navController.navigate("login_screen") {
                                popUpTo("user_screen") { inclusive = true }
                            }
                        }
                    }
                }
            }
        },
        drawerState = drawerState,
    ) {
        Scaffold(
            contentColor = Color.Black,
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    elevation = 0.dp,
                    title = { Text(text = stringResource(id = R.string.app_name),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = FontFamily.SansSerif
                        )
                        ) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Default.Menu, contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { /* Handle add user */ }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = Color.White),
            ) {
                // Content goes here
            }
        }
    }
}
@Composable
fun ThemeSwitcherScreen(
    languageViewModel: LanguageViewModel = hiltViewModel(),
    viewModel: ThemeModeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val themeMode by viewModel.themeMode.collectAsState()
    val languageCode by languageViewModel.languageCode.collectAsState()
    Column(modifier = Modifier.padding(16.dp)) {
       Box(
           modifier = Modifier
               .fillMaxWidth()

       ) {
          // Switch button
           Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                     .fillMaxWidth()

           ) {
             Icon(
                 imageVector =if (themeMode == "light") Icons.Outlined.ToggleOff else Icons.Outlined.ToggleOn,
                 contentDescription = "",
                 tint = MaterialTheme.colorScheme.primary,
                 modifier = Modifier
                     .size(50.dp)
                     .clickable {
                         viewModel.changeThemeMode(
                             if (themeMode == "light") "dark" else "light",
                             context
                         )
                     }
             )
                Text(
                    text = stringResource(
                        id = if (themeMode == "light") R.string.light_mode else R.string.dark_mode
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
           }

       }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Switch button
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector =if (languageCode == "en") Icons.Outlined.ToggleOff else Icons.Outlined.ToggleOn,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            languageViewModel.changeLanguage(
                                if (languageViewModel.languageCode.value == "en") "km" else "en",
                                context
                            )
                        }
                )
                Text(
                    text = stringResource(
                        id = if (languageCode == "en") R.string.english else R.string.khmer
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    MyScreen()
}
@Composable
fun MyScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Screen") },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Hello, World!", style = MaterialTheme.typography.headlineMedium)
            }
        }
    )
}
