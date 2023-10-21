package com.example.b2013527_quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.b2013527_quizapp.databinding.ActivityQuizQuestionBinding
import com.example.b2013527_quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val correctAnswer = intent.getIntExtra("correctAnswer", 0)
        val totalQuestion = intent.getIntExtra("totalQuestion", 0)

        binding.tvName.text = name
        binding.tvScore.text = "Your score is $correctAnswer out of $totalQuestion"

        binding.btnFinish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).setAction("your.custom.action")
            startActivity(intent)
            finish()
        }
    }
}