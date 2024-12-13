package com.example.android_lab4

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    val questionList = listOf(
        Question(R.string.question_1, true),
        Question(R.string.question_2, true),
        Question(R.string.question_3, false),
        Question(R.string.question_4, true),
        Question(R.string.question_5, true),
        Question(R.string.question_6, true),
        Question(R.string.question_7, false),
        Question(R.string.question_8, true),
        Question(R.string.question_9, true),
        Question(R.string.question_10, true)
    )

    var currentIndex = 0
    var countRightAnswers = 0
    var isCheater = false

    init {

    }

    val currentQuestionAnswer: Boolean get() = questionList[currentIndex].answer
    val currentQuestionText: Int get() = questionList[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionList.size
    }

    fun isEndQuestion():Boolean {
        if(currentIndex == questionList.size - 1)
        {
            return true
        }

        return false
    }

    override fun onCleared() {
        super.onCleared()
    }

}