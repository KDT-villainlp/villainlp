package com.villainlp.novlahvlah.novel.myNovel

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.villainlp.novlahvlah.novel.common.AuthorText
import com.villainlp.novlahvlah.novel.common.DeleteNovelCard
import com.villainlp.novlahvlah.novel.common.DescriptionText
import com.villainlp.novlahvlah.novel.common.FrontArrowImage
import com.villainlp.novlahvlah.novel.common.SwipeableBox
import com.villainlp.novlahvlah.novel.common.TitleText
import com.villainlp.novlahvlah.novel.common.TopBarTitle
import com.villainlp.novlahvlah.novel.common.deleteContents
import com.villainlp.novlahvlah.shared.MyScaffold
import com.villainlp.novlahvlah.shared.RelayNovel
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyBookScreen(
    navController: NavHostController,
    auth: FirebaseAuth,
    viewModel: MyNovelViewModel = viewModel(),
) {
    val novelList by viewModel.novelList.collectAsState()

    viewModel.loadNovels(auth)

    MyScaffold(TopBarTitle.MyNovel.title, navController) {
        MyNovels(it, navController, auth, novelList, viewModel)
    }
}

// 내 소설들을 모아둔 Column
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyNovels(
    modifier: Modifier,
    navController: NavHostController,
    auth: FirebaseAuth,
    myBooks: List<RelayNovel>,
    viewModel: MyNovelViewModel,
) {
    LazyColumn(
        modifier = modifier.padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(myBooks, key = { item -> item.documentID ?: "ERROR" }) { book ->

            SwipeableBox(
                modifier = Modifier.animateItemPlacement(),
                onConfirmValueChange = { deleteContents(it) { viewModel.onDeleteClicked(book, auth) } },
                backgroundContent = { color ->
                    DeleteNovelCard(
                        color = color,
                        text = "삭제하기",
                        cardHeight = 133.dp,
                        imageVector = Icons.Default.Delete
                    )
                },
                content = { NovelCard(navController, book) }
            )

            Spacer(modifier = Modifier.fillMaxWidth().size(15.dp))
        }
    }
}

@Composable
private fun NovelCard(
    navController: NavHostController,
    book: RelayNovel,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // LazyColumn의 넓이에 맞춤
            .height(133.dp)
            .clickable { navController.navigate("ReadMyBookScreen/${book.title}/${book.script}") },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier.padding(16.dp), // Content가 너무 딱 붙어서 패딩을 줌
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween, // 요소들을 가로 방향으로 공간을 균등하게 배치
        ) {
            Column(modifier = Modifier.weight(1f)) { // 왼쪽에 배치되는 열을 확장하여 오른쪽에 빈 공간을 만듦, 화살표가 보이게 하기위해
                NovelTitleAndScript(book)
                NovelAuthor(book)
            }
            FrontArrowImage() // 오른쪽에 배치
        }
    }
}

// 소설 제목, 내용 부분의 Row
@Composable
fun NovelTitleAndScript(book: RelayNovel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            TitleText(book.title)
            Spacer(modifier = Modifier.padding(top = 2.dp))
            DescriptionText(book.script)
        }
    }
}

// 소설 저자를 나타내는 Row
@Composable
fun NovelAuthor(book: RelayNovel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        AuthorText(book.author)
    }
}