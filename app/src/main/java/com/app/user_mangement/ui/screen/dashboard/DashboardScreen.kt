package com.app.user_mangement.ui.screen.dashboard

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.app.klakmoum.R
import com.app.user_mangement.ui.screen.auth.login.LoginViewModel
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.app.user_mangement.ui.screen.dashboard.user.user_list.LanguageViewModel
import com.app.user_mangement.ui.screen.dashboard.user.user_list.ThemeModeViewModel
import kotlinx.coroutines.launch
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    themeModeViewModel: ThemeModeViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val user by viewModel.user.collectAsState()
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    val themeModeState by themeModeViewModel.themeMode.collectAsState()
    val languageState by languageViewModel.languageCode.collectAsState()

    LaunchedEffect(loginState){
        viewModel.getUserData()
    }
    Scaffold(
    bottomBar = {
        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 60.dp),
            containerColor = Color.White
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Social Icon",
                    modifier = Modifier
                        .width(width = 84.dp)
                        .height(height = 32.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Social Icon",
                    modifier = Modifier.size(32.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Social Icon",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
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
                        text = "Hello, ${user?.name} \uD83C\uDF3F",
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
                                scope.launch {
                                    showSheet = true
                                }
                            }
                    )
                }
                Spacer(Modifier.height(16.dp))
                CarouselSlider()
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CustomInfoBox(
                        title = "Humidity",
                        icon = R.drawable.humidity,
                        number = "74%"
                    )
                    CustomInfoBox(
                        title = "Temperature",
                        icon = R.drawable.temperature,
                        number = "23°c"
                    )
                    CustomInfoBox(
                        title = "Water Level",
                        icon = R.drawable.drop,
                        number = "85%"
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CustomInfoBox(
                        title = "Connectivity",
                        icon = R.drawable.wife,
                        number = "Online"
                    )
                    CustomBigInfoBox(
                        title = "Nutrient Level",
                        subtitle = "5 grams left",
                        lastLine = "Refill in 2 days",
                        fistIcon = R.drawable.messure,
                        lastIcon = R.drawable.leaf
                    )

                }
                Spacer(Modifier.height(8.dp))
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    ){
                    CustomBigInfoBox(
                        title = "Status",
                        subtitle = "6 plants growing",
                        lastLine = "Next harvest in 3 days",
                        fistIcon = R.drawable.tree,
                        lastIcon = R.drawable.clock
                    )
                    CustomInfoBox(
                        title = "Light Status",
                        icon = R.drawable.lamp,
                        number = "On"
                    )
                }
                ModalBottomSheetDemo(
                    showSheet = showSheet,
                    onDismiss = { showSheet = false },
                    onThemeModeChange = {
                        themeModeViewModel.changeThemeMode(
                            if (themeModeState == "light") "dark" else "light",
                        )
                    },
                    themeState = themeModeState,
                    languageState = languageState,
                    onLanguageChange = {
                        languageViewModel.changeLanguage(
                            code = if(languageState == "en") "km"  else "en",
                            context = context
                        )
                    }
                )
            }
        }
    }
}
@Composable
fun CarouselSlider() {
    val images = listOf(
        R.drawable.flower, // replace with your 3 images
        R.drawable.image_flower,
        R.drawable.beautiful
    )
    val scopeCoroutine = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { images.size })
    LaunchedEffect(pagerState.currentPage) {
    }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(216.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            // Carousel Pager
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                pageSize = PageSize.Fixed(366.dp), // card size, adjust to your design
                contentPadding = PaddingValues(horizontal = 25.dp), // 👈 only 10.dp peek
                modifier = Modifier
                    .fillMaxWidth()
                    .height(216.dp)
            ) { page ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = images[page]),
                        contentDescription = "Carousel Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Box(
                modifier = Modifier
                    .width(320.dp) // make it a bit smaller than carousel
                    .height(85.dp)
                    .offset(y = 40.dp) // 👈 pushes it down so half overlaps
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Charlie's Garden",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 21.sp,
                                color = Color.Black,
                                fontWeight = FontWeight(600)
                            )
                        )
                        Text(
                            text = "ID: 1344295024",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 14.sp,
                                color = Color.Black,
                                fontWeight = FontWeight(400)
                            )
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.next),
                        contentDescription = "Next Icon",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                val nextPage = (pagerState.currentPage + 1) % images.size
                                scopeCoroutine.launch {
                                    pagerState.animateScrollToPage(nextPage)
                                }
                            }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(height = 50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            repeat(images.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(width = 16.dp)
                        .height(height = 6.dp)
                        .size(if (pagerState.currentPage == index) 10.dp else 8.dp)
                        .then(
                            Modifier
                                .background(
                                    color = if (pagerState.currentPage == index)
                                        Color(0xff0C9359)
                                    else
                                        Color(0xffD0ECE0),
                                    shape = RoundedCornerShape(50)
                                )
                        )
                )
            }
        }
    }
}
@Composable
fun CustomInfoBox(
    title : String ,
    @DrawableRes icon: Int,
    number : String
){
    Box(modifier = Modifier
        .width(width = 114.dp)
        .height(height = 114.dp)
        .background(color = Color.White, shape = RoundedCornerShape(12))
        ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Social Icon",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(400)
                )
            )
            Text(
                text = number,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(700)
                )
            )
        }
    }

}

@Composable
fun CustomBigInfoBox(
    title : String ,
    subtitle: String ,
    lastLine: String ,
    @DrawableRes fistIcon: Int,
    @DrawableRes lastIcon: Int
){

    Box(
        modifier = Modifier
            .width(width = 236.dp)
            .height(114.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(400)
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = fistIcon),
                    contentDescription = "Social Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(width = 3.8.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight(700)
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = lastIcon),
                    contentDescription = "Social Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(width = 3.8.dp))
                Text(
                    text = lastLine,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight(700)
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetDemo(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    onThemeModeChange : ()-> Unit,
    themeState : String,
    languageState :String,
    onLanguageChange: ()-> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            containerColor = Color.White,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(313.dp) // fixed height
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.light_status),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.Black,
                        fontSize = 21.sp,
                        fontWeight = FontWeight(600)
                    ),
                )
                Spacer(modifier = Modifier.height(30.dp))
                DivideLine()
                Spacer(modifier = Modifier.height(30.dp))
                ToggleFunction(title = "theme", state = themeState,
                    onToggle = {onThemeModeChange()})
                Spacer(modifier = Modifier.height(30.dp))
                DivideLine()
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Text(
                        text = "Automatic Settings",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color.Black,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.SemiBold // 600
                        )
                    )
                    // Off at Sunset + Icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Off at Sunset",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = Color(0xFF447761),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal // 400
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // spacing between text and icon
                        Image(
                            painter = painterResource(id = R.drawable.arrow_forward),
                            contentDescription = "Toggle Icon",
                            modifier = Modifier
                                .size(width = 68.dp, height = 32.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(31.dp))
                Text(
                    text = stringResource(id = R.string.go_to_setting),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color(0xff0C9359),
                        fontSize = 21.sp,
                        fontWeight = FontWeight(700)
                    ),
                )
            }
        }
    }
}

@Composable
fun ToggleFunction(
    title: String,
    state: String,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = when {
                title.equals("theme", ignoreCase = true) -> {
                    if (state == "light") stringResource(id = R.string.light_mode)
                    else stringResource(id = R.string.dark_mode)
                }
                title.equals("language", ignoreCase = true) -> {
                    if (state == "en") stringResource(id = R.string.english)
                    else stringResource(id = R.string.khmer)
                }
                else -> state // fallback
            },
            style = MaterialTheme.typography.labelLarge.copy(
                color = Color.Black,
                fontSize = 21.sp,
                fontWeight = FontWeight(600)
            ),
        )
        Image(
            painter = painterResource(
                id = when {
                    title.equals("theme", ignoreCase = true) -> {
                        if (state == "light")  R.drawable.toggle_on else R.drawable.toggle_off
                    }
                    title.equals("language", ignoreCase = true) -> {
                        if (state == "en") R.drawable.toggle_en else R.drawable.toggle_kh
                    }
                    else -> R.drawable.toggle_off // fallback
                }
            ),
            contentDescription = "Toggle Icon",
            modifier = Modifier
                .width(68.dp)
                .height(32.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onToggle() }
        )
    }
}

@Composable
fun DivideLine(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height = 1.dp)
        .padding(horizontal = 1.dp)
        .background(color = Color(0xffE6EDEA))
    )
}