package com.example.villainlp.chat.openAichat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.villainlp.GenNovelViewModelFactory
import com.example.villainlp.R
import com.example.villainlp.server.FirebaseTools
import com.example.villainlp.library.NovelInfo
import com.example.villainlp.ui.theme.Blue789
import com.example.villainlp.shared.MyScaffold
import com.example.villainlp.shared.ShowChats
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ChatListScreen(navController: NavHostController, auth: FirebaseAuth) {
    val chatListViewModel: ChatListViewModel = viewModel(factory = GenNovelViewModelFactory(auth, ChatModel()))

    val showDialog by chatListViewModel.showDialog.collectAsState()
    val documentID by chatListViewModel.documentID.collectAsState()
    val novelInfo by chatListViewModel.novelInfo.collectAsState()

    val firePuppleLottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.fire_pupple))

    MyScaffold("내 작업 공간", navController) {
        ShowChats(it, navController, novelInfo) { selectedChatting ->
            chatListViewModel.showDialog(selectedChatting.documentID ?: "ERROR")
        }
    }

    if (showDialog) {
        AlertDialog(
            icon = {
                LottieAnimation(
                    modifier = Modifier.size(40.dp),
                    composition = firePuppleLottie,
                    iterations = LottieConstants.IterateForever
                )
            },
            containerColor = Color.White,
            onDismissRequest = { chatListViewModel.hideDialog() },
            title = {
                Text(
                    text = "정말로 삭제하시겠습니까?",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "내 작업 공간에서 선택한 소설이 삭제가 됩니다.",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )
                }
            },
            confirmButton = {
                IconButton(
                    onClick = {
                        chatListViewModel.deleteChatting()
                        chatListViewModel.fetchChatList()  // 채팅 삭제 후 채팅 목록을 다시 불러옵니다.
                    }
                ) {
                    Text(
                        text = "확인",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blue789
                        )
                    )
                }
            },
            dismissButton = {
                IconButton(
                    onClick = { chatListViewModel.hideDialog() }
                ) {
                    Text(
                        text = "취소",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blue789
                        )
                    )
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
