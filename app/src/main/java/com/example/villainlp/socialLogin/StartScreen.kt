package com.example.villainlp.socialLogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.villainlp.R
import com.example.villainlp.shared.Screen
import com.example.villainlp.chat.openAichat.TextContent
import com.example.villainlp.ui.theme.Blue789
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LottieScreen(navController: NavController, auth: FirebaseAuth) {
    StartLottie(navController, auth)
}

@Composable
private fun StartLottie(navController: NavController, auth: FirebaseAuth) {
    // 애니메이션 시작 화면
    val robotlottie by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.robot)
    )
    var index by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val user = auth.currentUser

    val startDestination = if (user == null) Screen.Login.route else Screen.CreativeYard.route

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Blue789)
    ) {
        Box {
            Row(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                LottieAnimation(
                    composition = robotlottie,
                    modifier = Modifier.fillMaxWidth(),
                    iterations = LottieConstants.IterateForever
                )
            }
            Box(
                modifier = Modifier.padding(top = 320.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image99),
                    contentDescription = "그림자 이미지",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(start = 60.dp, end = 25.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        Descriptions(StartUiState.values()[index].content)
    }
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.progress22),
            contentDescription = "다음 화면으로 이동 버튼",
            modifier = Modifier
                .padding(bottom = 40.dp, end = 30.dp)
                .clickable {
                    // 화면 전환하기
                    if (index < StartUiState.values().size - 1) {
                        index++
                    } else {
                        navController.navigate(startDestination)
                    }

                },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun Descriptions(content: TextContent) {
    Column(
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 60.dp)
    ) {
        CustomText(
            text = content.subtitle,
            fontSize = 24,
            lineHeight = 40,
            fontWeight = 700,
            color = Color(0xFFFFFFFF)
        )
        CustomText(
            text = content.body,
            fontSize = 16,
            lineHeight = 24,
            fontWeight = 500,
            color = Color(0xFFFFFFFF)
        )
    }
}

@Composable
fun CustomText(
    text: String,
    fontSize: Int,
    lineHeight: Int,
    fontWeight: Int,
    color: Color
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize.sp,
            lineHeight = lineHeight.sp,
            fontWeight = FontWeight(fontWeight),
            color = color,
        )
    )
}
