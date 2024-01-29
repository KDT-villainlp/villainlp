@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.villainlp.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.villainlp.GenNovelViewModelFactory
import com.example.villainlp.R
import com.example.villainlp.chat.openAichat.ChatModel
import com.example.villainlp.shared.MyScaffoldBottomBar
import com.example.villainlp.shared.MyScaffoldTopBar
import com.example.villainlp.shared.SharedObjects.EMAIL
import com.example.villainlp.shared.SharedObjects.NAME
import com.example.villainlp.shared.SharedObjects.NOTION_INQUIRY
import com.example.villainlp.shared.SharedObjects.NOTION_LOGO
import com.example.villainlp.shared.SharedObjects.NOTION_URL
import com.example.villainlp.shared.SharedObjects.PROFILE_IMAGE
import com.example.villainlp.ui.theme.Blue789
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SettingScreen(
    auth: FirebaseAuth,
    navController: NavHostController,
    signOutClicked: () -> Unit,
) {
    val url = NOTION_URL
    val context = LocalContext.current

    // ViewModel 인스턴스 생성
    val viewModel: SettingViewModel =
        viewModel(factory = GenNovelViewModelFactory(auth, ChatModel()))

    // StateFlow의 값을 가져오기 위해 collectAsState 함수를 사용합니다.
    val userImage by viewModel.userImage.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userEmail by viewModel.userEmail.collectAsState()

    MyScaffoldProfile(
        title = "설정",
        navController = navController
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.padding(vertical = 30.dp))

            // Display user profile image
            DisplayUserProfileImage(userImage)

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            // Display user fields
            DisplayUserFields(
                userName = userName ?: "",
                onUserNameChange = {},
                userEmail = userEmail ?: "",
                onUserEmailChange = {}
            )

            // Display sign out button
            DisplaySignOutButton { signOutClicked() }
            // Display customer inquiry
            DisplayCustomerInquiry(url, context)
        }
    }
}


// 사용자 프로필 사진
@Composable
fun DisplayUserProfileImage(userImage: Uri?) {
    userImage?.let { imageUrl ->
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = PROFILE_IMAGE,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFF17C3CE), CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

// 로그아웃 버튼
@Composable
fun DisplaySignOutButton(signOutClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                spotColor = Color(0x5917C3CE),
                ambientColor = Color(0x5917C3CE)
            )
            .width(320.dp)
            .height(60.dp)
            .background(color = Color(0xFF17C3CE), shape = RoundedCornerShape(size = 17.dp))
            .padding(vertical = 14.dp)
            .clickable { signOutClicked() }
    ) {
        Text(
            text = "Sign out",
            modifier = Modifier
                .padding(
                    start = 120.dp,
                    end = 100.dp
                ),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                letterSpacing = 0.48.sp,
            )
        )
    }
}

// 고객 문의 및 FAQ
@Composable
fun DisplayCustomerInquiry(url: String, context: Context) {
    var isClicked by remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.padding(top = 70.dp))
    Box(
        modifier = Modifier
            .clickable {
                isClicked = true
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.notion1),
                contentDescription = NOTION_LOGO
            )
            // 고객 문의 텍스트
            Text(
                text = NOTION_INQUIRY,
                modifier = Modifier
                    .padding(start = 3.dp, end = 12.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    letterSpacing = 0.28.sp,
                )
            )
            Spacer(modifier = Modifier.padding(bottom = 30.dp))
        }
    }
}

// 사용자 이름, 이메일
@Composable
fun DisplayUserFields(
    userName: String,
    onUserNameChange: (String) -> Unit,
    userEmail: String,
    onUserEmailChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = userName,
        onValueChange = onUserNameChange,
        modifier = Modifier
            .width(320.dp)
            .height(80.dp)
            .padding(8.dp),
        label = { Text(NAME) },
        singleLine = true,
        enabled = false, // 편집 불가능하게 설정
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Blue789,
            unfocusedBorderColor = Blue789
        )
    )
    OutlinedTextField(
        value = userEmail,
        onValueChange = onUserEmailChange,
        modifier = Modifier
            .width(320.dp)
            .height(80.dp)
            .padding(8.dp),
        label = { Text(EMAIL) },
        singleLine = true,
        enabled = false, // 편집 불가능하게 설정
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Blue789,
            unfocusedBorderColor = Blue789
        )
    )
}

// Scaffold
@Composable
fun MyScaffoldProfile(
    title: String,
    navController: NavHostController,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        topBar = {
            MyScaffoldTopBar(title)
        },
        bottomBar = {
            MyScaffoldBottomBar(navController)
        }
    ) {
        content(
            Modifier
                .padding(it)
        )
    }
}
