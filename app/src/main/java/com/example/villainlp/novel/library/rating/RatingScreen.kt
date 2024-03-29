package com.example.villainlp.novel.library.rating

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.villainlp.R
import com.example.villainlp.ui.theme.Primary

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RatingScreen(
    navController: NavHostController,
    documentId: String,
    viewModel: RatingViewModel = viewModel(),
) {
    // rating요소 5개의 상태를 나타냄
    val ratingViewState by viewModel.ratingViewState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        // 4.65인치 Small Phone으로 테스트 했을때, 버튼은 보이지 않아 Ui 수정
        RatingColumn(ratingViewState, viewModel, navController, documentId)
    }
}

// 별 모양이 있는 Column
@Composable
fun RatingColumn(
    ratingViewState: RatingOption,
    viewModel: RatingViewModel,
    navController: NavHostController,
    documentId: String,
){
    val ratingBars = listOf(
        StarFactors.Artistry.factor to ratingViewState.artistryRate,
        StarFactors.Original.factor to ratingViewState.originalityRate,
        StarFactors.Commercial.factor to ratingViewState.commercialViabilityRate,
        StarFactors.Literary.factor to ratingViewState.literaryMeritRate,
        StarFactors.Complete.factor to ratingViewState.completenessRate
    )
    Divider()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(ratingBars){ (question, rating) ->
            RatingBar(
                question = question,
                rating = rating,
                onRatingChanged = { viewModel.setRatingByQuestion(question, it) }
            )
        }
        // 이 부분은 Small Phone Ui에 맞추기 위해 바꾼 Ui
        item {
            RatingButtonRow(
                onBackButtonClick = { navController.popBackStack() },
                onSubmitClick = {
                    viewModel.submitRating(documentId)
                    navController.popBackStack()
                }
            )
        }
    }
}

// 별점을 주는 별들
@Composable
fun RatingBar(
    question: String,
    rating: Int,
    onRatingChanged: (Int) -> Unit,
) {
    //전달 받은 rating을 지역변수로 사용함
    var localRating by remember { mutableStateOf(rating) }

    Spacer(modifier = Modifier.size(16.dp))
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
            val isSelected = index < localRating
            val starIcon: Painter = if (isSelected) {
                painterResource(id = R.drawable.ic_star_filled)
            } else {
                painterResource(id = R.drawable.ic_star_outline) // 아이콘 테두리 색 변경
            }

            Image(
                painter = starIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        localRating = index + 1
                        onRatingChanged(localRating) // Int값을 반환하기 우해
                    }
            )
        }
    }
    Divider()
}

// 별점 제출하는 버튼이 있는 Row
@Composable
fun RatingButtonRow(
    onBackButtonClick: () -> Unit,
    onSubmitClick: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Button(
            onClick = { onBackButtonClick() },
            colors = ButtonDefaults.buttonColors(Color.Transparent) // 버튼 컬러를 배경색과 맞춤
        )
        {
            Text(
                text = "취소",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.yeongdeok_sea)),
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            )
        }
        Button(
            onClick = { onSubmitClick() },
            colors = ButtonDefaults.buttonColors(Color.Transparent) // 버튼 컬러를 배경색과 맞춤
        ) {
            Text(
                text = "제출",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.yeongdeok_sea)),
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            )
        }
    }
}


