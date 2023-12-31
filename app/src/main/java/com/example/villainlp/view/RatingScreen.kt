package com.example.villainlp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.villainlp.R
import com.example.villainlp.model.FirebaseTools
import com.example.villainlp.model.FirebaseTools.updateBookRating
import com.example.villainlp.ui.theme.Blue789
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RatingScreen(navController: NavHostController, documentId: String) {
    var artistryRate by remember { mutableStateOf(0) }
    var originalityRate by remember { mutableStateOf(0) }
    var commercialViabilityRate by remember { mutableStateOf(0) }
    var literaryMeritRate by remember { mutableStateOf(0) }
    var completenessRate by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                artistryRate = RatingBar(question = "작품성 (문장력과 구성력)")
                originalityRate = RatingBar(question = "독창성 (창조성과 기발함)")
                commercialViabilityRate = RatingBar(question = "상업성 (몰입도와 호소력)")
                literaryMeritRate = RatingBar(question = "문학성 (철학과 감명)")
                completenessRate = RatingBar(question = "완성도 (문체의 가벼움과 진지함)")
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center, // 버튼들을 수평 가운데로 정렬
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp) // 상하 여백 추가
        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                          },
                colors = ButtonDefaults.buttonColors(Color.White)
            )
            {
                Text(
                    text = "취소",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue789
                    )
                )
            }
            Button(
                onClick = {
                    scope.launch {
                        val book = FirebaseTools.getLibraryBookFromFirestore(documentId)
                        var averageRate = book[0].totalRate
                        var starCount = book[0].starCount
                        averageRate += (artistryRate + originalityRate + commercialViabilityRate + literaryMeritRate + completenessRate) / 5.0f
                        starCount += 1
                        updateBookRating(documentId, averageRate, starCount)
                    }
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(
                    text = "제출",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue789
                    )
                )
            }
        }
    }
}

@Composable
fun RatingBar(question: String): Int {
    var rating by remember { mutableStateOf(0) }

    Text(text = question)
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display stars based on the selected rating
        (0 until 5).forEach { index ->
            val isSelected = index < rating
            val starIcon: Painter = if (isSelected) {
                painterResource(id = R.drawable.ic_star_filled)
            } else {
                painterResource(id = R.drawable.ic_star_outline)
            }

            Image(
                painter = starIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        rating = index + 1
                    }
            )
        }
    }
    Divider()

    return rating
}