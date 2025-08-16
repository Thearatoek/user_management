package com.app.user_mangement.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.klakmoum.R
import com.app.user_mangement.ui.widget.CustomButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xff0C9359), Color(0xff0EAD69))
                    )
                )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Welcome Image",
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "AEPOD",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                    )
                }
                // Space
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Welcome to Aepod",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight(500)
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Grow plants easily from your home with our award-winning pods",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(500)
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(modifier = Modifier.height(70.dp))
                CustomButton(
                    isRegister = true,
                    text = "Register",
                    onClick = { navController.navigate("register_screen") },
                )
                Spacer(modifier = Modifier.height(20.dp))
                CustomButton(
                    isRegister = false,
                    text = "Login",
                    onClick = {
                        navController.navigate("login_screen")
                    },
                )
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}