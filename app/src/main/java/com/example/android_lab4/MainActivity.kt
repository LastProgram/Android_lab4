package com.example.android_lab4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?:0

        setContentView(R.layout.activity_main)

        trueButton = findViewById<Button>(R.id.true_button)
        falseButton = findViewById<Button>(R.id.false_button)
        nextButton = findViewById<Button>(R.id.next_button)
        cheatButton = findViewById<Button>(R.id.cheat_button)
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

        cheatButton.setOnClickListener { _ ->
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)

            startActivityForResult(intent, REQUEST_CODE_CHEAT)
            cheatButton.visibility = View.INVISIBLE
        }

        if(!checkResultOutput()){
            updateQuestion()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT)
        {
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
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

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judjment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        if(userAnswer == correctAnswer)
        {
            quizViewModel.countRightAnswers++
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
        if(cheatButton.visibility == View.VISIBLE)
        {
            cheatButton.visibility = View.INVISIBLE
        }
    }

    fun showAnswerButtons()
    {
        trueButton.visibility = View.VISIBLE;
        falseButton.visibility = View.VISIBLE;

        if(cheatButton.visibility == View.INVISIBLE)
        {
            cheatButton.visibility = View.VISIBLE
        }
    }
}