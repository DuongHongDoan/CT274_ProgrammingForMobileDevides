package com.example.b2013527_quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.b2013527_quizapp.databinding.ActivityMainBinding
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            if (binding.etName.text.contentEquals("")){
                Toast.makeText(this, "Please enter your name?", Toast.LENGTH_LONG).show()
            }
            else{
                val intent = Intent(this, QuizQuestionActivity::class.java).setAction("your.custom.action")
                val name = binding.etName.text.toString()
                intent.putExtra("name", name)
                startActivity(intent)
                finish()
            }
        }
    }
}