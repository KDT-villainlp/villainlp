package com.example.villainlp.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.villainlp.R
import com.example.villainlp.ui.theme.Primary

// CreateGround, ChatListScreen, MyNovelScreen
@Composable
fun MyScaffold(
    title: String,
    navController: NavHostController,
    content: @Composable (Modifier) -> Unit,
) {
    // 다크모드, 라이트모드에 따라 배경색 변경
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White

    Scaffold(topBar = {
        MyScaffoldTopBar(title)
    }, bottomBar = {
        MyScaffoldBottomBar(navController)
    }) {
        content(
            Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = backgroundColor),
        )
    }
}

// SettingScreen, MyScaffold(UiFunctions)
@Composable
fun MyScaffoldBottomBar(navController: NavHostController) {
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val currentScreen = remember { mutableStateOf(navController.currentDestination?.route) }
    val windowSize = LocalConfiguration.current.screenWidthDp.dp

    Column {
        Divider(thickness = 0.5.dp, color = Color(0xFF9E9E9E))
        Row(
            Modifier
                .width(428.dp)
                .height(80.dp)
                .background(color = backgroundColor, RoundedCornerShape(17.dp))
                .padding(start = 33.dp, top = 16.dp, end = 33.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            // Child views.
            CustomIconButton(
                defaultIcon = R.drawable.home,
                clickedIcon = R.drawable.home_clicked,
                isCurrentScreen = currentScreen.value == Screen.CreativeYard.route,
                iconText = "창작마당",
                clickedTextColor = Primary,
                defaultTextColor = Color(0xFFbbbbbb)
            ) { navController.navigate(Screen.CreativeYard.route) }
            CustomIconButton(
                defaultIcon = R.drawable.forum,
                clickedIcon = R.drawable.forum_clicked,
                isCurrentScreen = currentScreen.value == Screen.ChattingList.route,
                iconText = "릴레이소설",
                clickedTextColor = Primary,
                defaultTextColor = Color(0xFFbbbbbb)
            ) { navController.navigate(Screen.ChattingList.route) }
            CustomIconButton(
                defaultIcon = R.drawable.book_5,
                clickedIcon = R.drawable.book_clicked,
                isCurrentScreen = currentScreen.value == Screen.MyBook.route,
                iconText = "내서재",
                clickedTextColor = Primary,
                defaultTextColor = Color(0xFFbbbbbb)
            ) { navController.navigate(Screen.MyBook.route) }
            CustomIconButton(
                defaultIcon = R.drawable.local_library,
                clickedIcon = R.drawable.local_library_clicked,
                isCurrentScreen = currentScreen.value == Screen.Library.route,
                iconText = "도서관",
                clickedTextColor = Primary,
                defaultTextColor = Color(0xFFbbbbbb)
            ) { navController.navigate(Screen.Library.route) }
            CustomIconButton(
                defaultIcon = R.drawable.settings,
                clickedIcon = R.drawable.settings_clicked,
                isCurrentScreen = currentScreen.value == Screen.Profile.route,
                iconText = "설정",
                clickedTextColor = Primary,
                defaultTextColor = Color(0xFFbbbbbb)
            ) { navController.navigate(Screen.Profile.route) }
        }
    }
}

// SettingScreen, MyScaffold(UiFunctions)
@Composable
fun MyScaffoldTopBar(title: String) {
    val isDarkTheme = isSystemInDarkTheme()
    val windowSize = LocalConfiguration.current.screenWidthDp.dp
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(windowSize * 0.18f)
                .padding(windowSize * 0.016f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(600),
                    color = if (isDarkTheme) Color.White else Color.DarkGray
                )
            )
        }
        Divider(color = if (isDarkTheme) Color.LightGray else Color.LightGray)
    }
}

// MyScaffoldBottomBar랑 연결
@Composable
fun CustomIconButton(
    defaultIcon: Int,
    clickedIcon: Int,
    isCurrentScreen: Boolean,
    iconText: String,
    clickedTextColor: Color,
    defaultTextColor: Color,
    onClicked: () -> Unit,
) {
    val icon = if (isCurrentScreen) clickedIcon else defaultIcon
    val textColor = if (isCurrentScreen) clickedTextColor else defaultTextColor

    Column(
        modifier = Modifier.clickable { onClicked() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(0.dp)
                .width(28.dp)
                .height(28.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.height(17.dp), text = iconText, style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight(500),
                color = textColor,
            )
        )
    }
}