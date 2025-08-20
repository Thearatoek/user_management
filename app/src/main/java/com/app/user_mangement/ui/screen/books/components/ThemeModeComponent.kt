package com.app.user_mangement.ui.screen.books.components

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ToggleOff
import androidx.compose.material.icons.outlined.ToggleOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.klakmoum.R
import com.app.user_mangement.ui.screen.dashboard.user.user_list.LanguageViewModel
import com.app.user_mangement.ui.screen.dashboard.user.user_list.ThemeModeViewModel
@Composable
fun ThemeSwitcherScreen(
    languageViewModel: LanguageViewModel = hiltViewModel(),
    viewModel: ThemeModeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = context as? Activity
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
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            viewModel.changeThemeMode(
                                if (themeMode == "light") "dark" else "light",
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
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
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
                    imageVector =if (languageCode == "en") Icons.Outlined.ToggleOff else Icons.Outlined.ToggleOn,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                        }
                )
                Text(
                    text = stringResource(
                        id = if (languageCode == "en") R.string.english else R.string.khmer
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}