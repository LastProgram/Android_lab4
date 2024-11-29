package com.example.android_lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

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

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionText: TextView

    var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("currentIndex")
        }

        setContentView(R.layout.activity_main)

        trueButton = findViewById<Button>(R.id.true_button)
        falseButton = findViewById<Button>(R.id.false_button)
        nextButton = findViewById<Button>(R.id.next_button)
        questionText = findViewById<TextView>(R.id.question_text)


        trueButton.setOnClickListener { _ ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { _ ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener { _ ->
            currentIndex = (currentIndex + 1) % questionList.size
            updateQuestion(currentIndex)
            showAnswerButtons()
        }

        updateQuestion(currentIndex)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentIndex", currentIndex)
    }

    fun updateQuestion(currentIndex: Int)
    {
        val questionTextResId = getString(questionList[currentIndex].textResId)
        questionText.setText(questionTextResId)
    }

    fun checkAnswer(userAnswer: Boolean)
    {
        val correctAnswer = questionList[currentIndex].answer

        val messageResId = if(userAnswer == correctAnswer)
        {
            R.string.correct_toast
        }
        else
        {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        hideAnswerButtons()
    }

    fun hideAnswerButtons()
    {
        trueButton.visibility = View.INVISIBLE;
        falseButton.visibility = View.INVISIBLE;
    }

    fun showAnswerButtons()
    {
        trueButton.visibility = View.VISIBLE;
        falseButton.visibility = View.VISIBLE;
    }
}