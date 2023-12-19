package com.example.villainlp.model

sealed class Screen(val route: String) {
    object Profile : Screen("UserProfileScreen")
    object Lottie : Screen("LottieScreen")
    object Login : Screen("LoginScreen")
    object Chatting : Screen("ChattingScreen/{title}/{threadId}/{assistantKey}")
    object CreativeYard : Screen("CreativeYardScreen")
    object Library : Screen("LibraryScreen")
    object MyBook : Screen("MyBookScreen")
    object ReadMyBook : Screen("ReadMyBookScreen/{title}/{script}")
    object ReadLibraryBook : Screen("ReadLibraryBookScreen/{title}/{script}/{documentId}")
    object Rating : Screen("RatingScreen/{documentId}")
    object ChattingList : Screen("ChattingScreen")
    object Comment : Screen("CommentScreen/{documentId}")

}