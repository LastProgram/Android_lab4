package com.example.android_lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?:0

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
           quizViewModel.moveToNext()
            updateQuestion()
            showAnswerButtons()
        }

        if(!checkResultOutput()){
            updateQuestion()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    fun updateQuestion()
    {
        val questionTextResId = quizViewModel.currentQuestionText
        questionText.setText(questionTextResId)
    }

    fun checkAnswer(userAnswer: Boolean)
    {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId: String
        if(userAnswer == correctAnswer)
        {
            quizViewModel.countRightAnswers++
            messageResId = R.string.correct_toast.toString()
        }
        else
        {
            messageResId = R.string.incorrect_toast.toString()
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        hideAnswerButtons()
        checkResultOutput()
    }

    fun checkResultOutput() : Boolean
    {
        if(quizViewModel.isEndQuestion())
        {
            nextButton.visibility = View.INVISIBLE
            val finalText = "Правильных ответов: " + quizViewModel.countRightAnswers.toString()
            questionText.setText(finalText)
        }

        return quizViewModel.isEndQuestion()
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