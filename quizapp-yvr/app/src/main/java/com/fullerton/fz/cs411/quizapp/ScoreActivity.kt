package com.fullerton.fz.cs411.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.fullerton.fz.cs411.quizapp.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_score)
        setContentView(binding.root)
//        findViewById<TextView>(R.id.score).setText("Congrats!!! Your Score is ${intent.getIntExtra("SCORE", 0)}")
//
        binding.score.setText("Congrats!!! Your Score is ${intent.getIntExtra("SCORE", 0)}")

    }

}