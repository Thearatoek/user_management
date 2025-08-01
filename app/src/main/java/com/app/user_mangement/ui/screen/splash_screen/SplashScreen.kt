package com.app.user_mangement.ui.screen.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.app.klakmoum.R
import com.app.user_mangement.ui.screen.auth.login.LoginViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import org.example.user.management.sample.data.model.LoginUiState

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val state by viewModel.loginState.collectAsState()
    LaunchedEffect(key1 = true) {
        delay(2000)
       viewModel.checkUserLoginAction()
        delay(100)
       if(state is LoginUiState.Success){
           navController.navigate("Dashboard")
       }else{
           navController.navigate("login_screen")
       }
    }
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = Color(0xff317a59),
            darkIcons =false
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) { padding ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(color =  MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(size = 60.dp),
                    painter = painterResource(id = R.drawable.cup),
                    contentDescription = stringResource(id = R.string.cup)
                )
                Box(modifier = Modifier.padding(8.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "LINH SHOP", color = Color.White, style = TextStyle(
                        fontSize = 30.sp, fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight(1000)
                    )
                )
            }
        }

    }
}