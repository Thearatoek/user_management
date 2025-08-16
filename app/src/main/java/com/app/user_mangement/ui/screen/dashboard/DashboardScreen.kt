package com.app.user_mangement.ui.screen.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.app.klakmoum.R
import com.app.user_mangement.ui.screen.auth.login.LoginViewModel
import org.example.user.management.sample.data.model.LoginUiState

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.loginState.collectAsStateWithLifecycle()
    LaunchedEffect(state){
        if(state is LoginUiState.Success){
            navController.navigate("login_screen")
        }
    }
    Scaffold(
        modifier = Modifier
            .background(color = Color.Red)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color(0xffedf9f6))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Spacer(Modifier.height(64.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Hello, Isaac",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color.Black,
                            fontSize = 32.sp,
                            fontWeight = FontWeight(500)
                        ),
                        modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                    )
                    Image(
                        painter = painterResource(id = R.drawable.setting),
                        contentDescription = "Social Icon",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                viewModel.logout()
                            }
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}