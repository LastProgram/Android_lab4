package com.example.android_lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val trueButton = findViewById<Button>(R.id.true_button)
        val falseButton = findViewById<Button>(R.id.false_button)
        val nextButton = findViewById<Button>(R.id.next_button)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton.setOnClickListener { _ ->

        }

        falseButton.setOnClickListener { _ ->

        }

        nextButton.setOnClickListener { _ ->

        }
    }
}