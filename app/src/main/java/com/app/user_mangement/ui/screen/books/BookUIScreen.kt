package com.app.user_mangement.ui.screen.books

import BookItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.klakmoum.R
import com.app.user_mangement.data.model.BookModel
import com.app.user_mangement.data.model.UiState
import com.app.user_mangement.ui.screen.auth.login.LoginViewModel
import com.app.user_mangement.ui.screen.auth.login.components.LoginButton
import com.app.user_mangement.ui.screen.books.components.ThemeSwitcherScreen
import com.app.user_mangement.ui.screen.user.user_list.LanguageViewModel
import com.app.user_mangement.ui.screen.user.user_list.ThemeModeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import org.example.user.management.sample.data.model.LoginUiState

@Composable
fun BookScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    themeModeViewModel: ThemeModeViewModel = hiltViewModel(),
    bookViewModel : BookViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val systemUiController = rememberSystemUiController()
    val state by loginViewModel.loginState.collectAsState()
    val bookState by bookViewModel.bookListing.collectAsState()
    val themeCode = themeModeViewModel.themeMode.collectAsState()
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
            ) {
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
                    ThemeSwitcherScreen(
                        viewModel = themeModeViewModel,
                        languageViewModel = languageViewModel
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    LoginButton(state, scope, stringResource(id = R.string.logout)) {
                        loginViewModel.logout()
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

                    title = {
                    },
                    actions = {
                        IconButton(onClick = {
                        }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                        }
                    },
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
                    .padding(16.dp)
                    .background(color = Color.White),
            ) {
                // Content goes here
                when (bookState) {
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Success -> {
                        val books = (bookState as UiState.Success<List<BookModel>>).data
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(books) { book ->
                                BookItem(book = book,
                                    onClick = {
                                        // Handle book click
                                    },
                                    onUpdate = {
                                        // Handle book update
                                        val updateBook = book.copy(cover_url = "https://plus.unsplash.com/premium_photo-1682125773446-259ce64f9dd7?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Ym9vayUyMGNvdmVyfGVufDB8fDB8fHww")
                                        bookViewModel.updateBook(updateBook)
                                    },
                                    onDelete = {
                                        // Handle book deletion
                                        bookViewModel.deleteBook(book.id)
                                    }
                                )
                            }
                        }
                    }
                    is UiState.Error -> {
                        Text(
                            text = (bookState as UiState.Error).message ?: "Error loading books",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

