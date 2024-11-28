package com.example.android_lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val trueButton = findViewById<Button>(R.id.true_button)
        val falseButton = findViewById<Button>(R.id.false_button)
        val nextButton = findViewById<Button>(R.id.next_button)
        val questionText = findViewById<TextView>(R.id.question_text)

        var currentIndex: Int = 0

        fun updateQuestion(currentIndex: Int)
        {
            val questionTextResId = getString(questionList[currentIndex].textResId)
            questionText.setText(questionTextResId)
        }

        trueButton.setOnClickListener { _ ->

        }

        falseButton.setOnClickListener { _ ->

        }


        nextButton.setOnClickListener { _ ->
            currentIndex = (currentIndex + 1) % questionList.size
            updateQuestion(currentIndex)
        }


    }
}