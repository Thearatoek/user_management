package com.app.user_mangement.ui.screen.auth.login.components

// Core Compose
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.user_mangement.ui.screen.auth.login.LoginUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@Composable
fun LoginButton(
    state: LoginUiState,
    scope: CoroutineScope,
    text : String,
    onClick: suspend () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background( MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                scope.launch { onClick() }
            },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.background,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
            if (state is LoginUiState.Loading) {
                Spacer(Modifier.width(10.dp))
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}
@Composable
fun SignUpPrompt(
    text : String,
    subText : String,
    onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text, style = TextStyle(
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        ))
        Text(
            text = subText,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.clickable {
               onClick()
            }
        )
    }
}
@Composable
fun DividerWithText(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.weight(1f).height(1.dp).background( MaterialTheme.colorScheme.onPrimaryContainer))
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp),
            color =  Color.Black,
            style = TextStyle(fontWeight = FontWeight.SemiBold)
        )
        Box(modifier = Modifier.weight(1f).height(1.dp).background( MaterialTheme.colorScheme.onPrimaryContainer))
    }
}
@Composable
fun SocialLoginButton(
    iconId: Int,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(1.dp,  MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                style = TextStyle(fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Normal)
            )
        }
    }
}