package com.app.user_mangement.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.user_mangement.ui.screen.auth.login.LoginUiState
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    isRegister: Boolean,
    isLoading: LoginUiState
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(52.dp)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(4.dp))
            .background(
                Color(if (isRegister) 0xffFFFFFF else 0xff0EAD69),
                shape = RoundedCornerShape(4.dp)
            ) // Use the appropriate color based on isRegister
            .clickable { onClick() },
        contentAlignment = Alignment.Center // Center the text
    ) {
       Row{
           Text(
               text = text,
               style = MaterialTheme.typography.labelLarge.copy(
                   color = if (isRegister) Color(0xff0C9359) else Color.White, // Change text color based on isRegister
                   fontSize = 18.sp,
                   fontWeight = FontWeight(700)
               ),
           )
          Spacer(modifier = Modifier.width(width = 18.dp))
           if(isLoading == LoginUiState.Loading)
               CircularProgressIndicator(
                   modifier = Modifier.size(25.dp),
                   color =Color(0xff0C9359)
               )
       }
    }
}