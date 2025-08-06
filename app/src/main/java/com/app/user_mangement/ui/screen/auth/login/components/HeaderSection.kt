package com.app.user_mangement.ui.screen.auth.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun HeaderSection(
    title : String,
    subtitle : String

) {
    Column {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 10.dp),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold ,
                color = Color.Black)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = subtitle,
            modifier = Modifier.padding(horizontal = 10.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black)
        )
    }
}
