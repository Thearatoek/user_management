package com.app.user_mangement.ui.screen.auth.login

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.klakmoum.R
import com.app.user_mangement.ui.widget.CustomButton

@Composable
fun SetupScreen(
   navController: NavController
) {
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush =  Brush.linearGradient(
                        colors = listOf( Color(0xFF0C9359),Color(0xFF0EAD69),), // start to end colors
                        start = Offset(0f, 0f),  // top-left
                        end = Offset(0f, 0f) // bottom-right
                    )
                ),
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)

            ) {
                Spacer(Modifier.height(120.dp))
                Text(
                    text = "Let’s set you up",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Sync your Aepods with the app for added functionality",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(start = 16.dp) // spacing between logo and text
                )
                Spacer(Modifier.height(40.dp))


                CustomContainerAppWidget(
                    onclick = { /*TODO*/ },
                    text = "Upstairs Pod",
                    id = "ID: 1344295024"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomContainerAppWidget(
                    onclick = { /*TODO*/ },
                    text = "Porch Pod",
                    id = "ID: 1344295024"
                )
                Spacer(Modifier.height(16.dp))
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp)
                        .background(with(Color.White.copy(alpha = 0.1f)) {
                            copy()
                        }, shape = RoundedCornerShape(16.dp))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { },
                ){
                    Row (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp), // padding inside the box
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Sync new Aepod",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = Color.White,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                        Image(
                            painter = painterResource(id = R.drawable.tick),
                            contentDescription = "Social Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                CustomButton(text = "Continue", onClick = { /*TODO*/ }, isRegister =true )
                Spacer(Modifier.height(25.dp))
            }
        }
    }
}


// custom user functionality widget
@Composable
fun CustomContainerAppWidget(
    onclick : () -> Unit,
    text :String ,
    id: String ,
){
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(horizontal = 16.dp)
            .background(color = Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onclick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp), // padding inside the box
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight(600)
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp)) // spacing between title and id
                Text(
                    text = id,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                )
            }
            Row {
                Text(
                    text = "Synced",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    ),
                )
                Spacer(modifier = Modifier.width(8.dp)) // spacing between text and icon
                Image(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = "Social Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}