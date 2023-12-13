package com.example.villainlp.model

data class Book(
    val title: String,
    val author: String,
    val description: String,
    val userID: String,
    ) {
    // constructor를 생성 안해주면 firebase store에 올라가지 않고 튕김
    constructor() : this("", "", "", "")
}
