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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.klakmoum.R
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
    val configuration = LocalConfiguration.current
    LaunchedEffect(state) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = themeCode.value == "light"
        )
        if (state is LoginUiState.Success) {
            navController.navigate("login_screen")
        }
    }
    val context = LocalContext.current

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
    )
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primary,
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
                            fontFamily = FontFamily.SansSerif
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
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    elevation = 0.dp,
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Default.Menu, contentDescription = null,
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
                    .background(color = MaterialTheme.colorScheme.background)
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
                 tint = if (themeMode == "light") Color(0xFFB0BEC5) else Color(0xFF37474F),
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
                    style = MaterialTheme.typography.bodyLarge
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
                    imageVector = Icons.Outlined.ToggleOff,
                    contentDescription = "",
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
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
    }
}


