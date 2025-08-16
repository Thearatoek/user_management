package com.app.user_mangement.ui.screen.auth.login.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.klakmoum.R


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    hint: String = "",
    @DrawableRes leadingIcon: Int? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 18.sp,
                    color = Color.White
                )
            )
        },
        placeholder = { Text(text = hint) },
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color.White.copy(alpha = 0.5f),
                    start = Offset(16.dp.toPx(), y), // start after 16.dp padding
                    end = Offset(size.width - 16.dp.toPx(), y), // end before 16.dp padding
                    strokeWidth = strokeWidth
                )
            },
        singleLine = true,
        leadingIcon = {
            leadingIcon?.let {
                Image(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "Social Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingIcon = {
            if (isPassword) {
                val icon = if (passwordVisible)  R.drawable.eye_open else R.drawable.eye_close

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "Social Icon",
                        modifier = Modifier.size(24.dp)

                    )
                }
            }
        },
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,   // hide default underline
            unfocusedIndicatorColor = Color.Transparent, // hide default underline
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            textColor = Color.White
        )
    )
}