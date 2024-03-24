package com.example.geoquiz
import androidx.annotation.StringRes

data class Question(
    @StringRes val headingResId: Int,
    @StringRes val textResId: Int,
    val answer: Boolean,
    var isAnswered: Boolean = false
)
