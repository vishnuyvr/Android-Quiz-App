package com.fullerton.fz.cs411.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {
    private lateinit var chooseQuiz : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        chooseQuiz = findViewById(R.id.QuizBtn)

        chooseQuiz.setOnClickListener {
            val intent = Intent(this, SelectQuizActivity::class.java) // need to change to selection screen

            startActivity(intent)
        }
    }


}