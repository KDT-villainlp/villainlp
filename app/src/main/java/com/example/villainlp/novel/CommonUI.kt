package com.example.villainlp.novel

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.villainlp.R
import com.example.villainlp.ui.theme.Primary

// LibraryScreen, CommentScreen, MyNovelScreen, ReadMyNovelScreen
@Composable
fun AlertPopup(
    title: String, // Alert에 Title에 해당
    message: String, // 만약 TextField가 있다면 TextField에 들어갈 메세지
    onDismiss: () -> Unit, // dismiss 동작
    onConfirm: () -> Unit, // 확인 동작
    novelTitle: String = "", // 책제목 또는 title 이외의 제목
    warningMessage: String = "", // TextField가 있을때 본문내용
    hasTextField: Boolean = false, // TextField 유무
    tfLabel: String = "", // TextField 라벨
    onTextFieldValueChange: (String) -> Unit = {}, // textField onChange
    confirmButtonText: String = "확인", // 확인버튼 텍스트
    dismissButtonText: String = "취소" // 취소버튼 텍스트
){
    val firePuppleLottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.fire_pupple))
    AlertDialog(
        icon = {
            LottieAnimation(
                modifier = Modifier.size(40.dp),
                composition = firePuppleLottie,
                iterations = LottieConstants.IterateForever
            )
        },
        containerColor = Color.White,
        onDismissRequest = { onDismiss() },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
            }
        },
        text = {
            if (hasTextField){
                Column {
                    Text(
                        text = "\"${novelTitle}\"$warningMessage",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange = { onTextFieldValueChange(it) },
                        modifier = Modifier
                            .width(300.dp)
                            .height(80.dp)
                            .padding(8.dp),
                        label = { Text(text = tfLabel) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Primary
                        ),
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = message,
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )
                }

            }
        },
        confirmButton = {
            IconButton(
                onClick = { onConfirm() }
            ) {
                Text(
                    text = confirmButtonText,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                )
            }
        },
        dismissButton = {
            IconButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    text = dismissButtonText,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                )
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}

// LibraryScreen, ReadBookScreen
fun formatRating(rating: Float): Float {
    val formattedString = String.format("%.2f", rating)
    return formattedString.toFloat()
}

// ReadMyNovelScreen, ReadBookScreen
@Composable
fun ReadScreenTopBar(
    title: String,
    navController: NavHostController,
    onClicked: () -> Unit = {},
    hasIcon: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 4.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "back")
            }
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF212121),
                )
            )
            if (hasIcon){
                IconButton(onClick = { onClicked() }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "upload")
                }
            } else {
                Spacer(modifier = Modifier.size(15.dp))
            }
        }
        Divider(color = Color(0xFF9E9E9E))
    }
}

// MyNovel, Library (Card에 화살표 이미지)
@Composable
fun FrontArrowImage(){
    Image(
        modifier = Modifier
            .size(33.dp)
            .padding(5.dp),
        painter = painterResource(id = R.drawable.arrow_right),
        contentDescription = "Front Arrow"
    )
}

// MyNovel, Library (제목 Text 속성)
@Composable
fun TitleText(title: String){
    Text(
        modifier = Modifier
            .wrapContentWidth()
            .height(30.dp),
        text = title,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = Color(0xFF212121),
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

// MyNovel, Library (요약 Text 속성)
@Composable
fun DescriptionText(description: String){
    Text(
        modifier = Modifier
            .wrapContentWidth()
            .height(45.dp),
        text = description,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF2C2C2C),
        ),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

// MyNovel, Library (저자 Text 속성)
@Composable
fun AuthorText(author: String){
    Text(
        text = author,
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF9E9E9E),
            textAlign = TextAlign.Start,
        )
    )
}

// Swipe 상태 관리에 필요한 Parameter들
@Composable
@OptIn(ExperimentalWearMaterialApi::class)
fun createSwipeableParameters(): SwipeableParameters {
    val swipeableState = rememberSwipeableState(initialValue = 0f)

    val swipeableModifier = Modifier.swipeable(
        state = swipeableState,
        anchors = mapOf(0f to 0f, -150f to -150f),
        orientation = Orientation.Horizontal,
        thresholds = { _, _ -> FractionalThreshold(0.1f) },
        resistance = null
    )

    val imageVisibility = swipeableState.offset.value <= -150f

    return SwipeableParameters(swipeableState, swipeableModifier, imageVisibility)
}

