package com.example.android_lab4

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.mainactivity.answer_is_true"
private const val EXTRA_VERSION_API = "com.bignerdranch.android.mainactivity.api_version"
private const val EXTRA_CHEAT_COUNT = "com.bignerdranch.android.mainactivity.cheat_count"

private lateinit var answerTextView: TextView
private lateinit var outputApiTextView: TextView
private lateinit var cheatCountTextView: TextView
private lateinit var showAnswerButton: Button


private var answerIsTrue = false
private var apiVersion = 0
private var cheatCount = 0

class CheatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        outputApiTextView = findViewById(R.id.output_api_text_view)
        cheatCountTextView = findViewById(R.id.cheat_count_text_view)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        apiVersion = intent.getIntExtra(EXTRA_VERSION_API, 0)
        cheatCount = intent.getIntExtra(EXTRA_CHEAT_COUNT, 0)

        outputApiTextView.setText("Версия API $apiVersion")
        cheatCountTextView.setText("Осталось $cheatCount подсказок")

        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
            val currentCheatCount = cheatCount - 1

            cheatCountTextView.setText("Осталось $currentCheatCount подсказок")
        }
    }


    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }


    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, apiVersion:Int, cheatCount:Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_VERSION_API, apiVersion)
                putExtra(EXTRA_CHEAT_COUNT, cheatCount)
            }
        }
    }
}