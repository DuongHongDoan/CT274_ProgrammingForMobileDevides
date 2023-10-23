package com.example.b2013527_quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.b2013527_quizapp.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizQuestionBinding
    private lateinit var name: String
    private var currentPosition: Int = 1
    private var correctAnswer: Int = 0
    private var selectedPosition: Int = 0
    private var lenList: Int = 0
    private lateinit var questionLists:ArrayList<Question>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //lay ten ra khoi intent
        name = intent.getStringExtra("name").toString()

        //lay danh sach cac cau hoi ra
        questionLists = Constants.getQuestions()

        //lay ra do dai cua danh sach cau hoi
        lenList = questionLists.size
        binding.progressBar.max = lenList

        //ham dung de do du lieu ra giao dien
        setQuestion()

        //tao su kien khi nhan vao cac o tra loi
        binding.tvOptionOne.setOnClickListener{selectAnswer(binding.tvOptionOne, 1)}
        binding.tvOptionTwo.setOnClickListener{selectAnswer(binding.tvOptionTwo, 2)}
        binding.tvOptionThree.setOnClickListener{selectAnswer(binding.tvOptionThree, 3)}
        binding.tvOptionFour.setOnClickListener{selectAnswer(binding.tvOptionFour, 4)}

        //tao su kien khi nhan nut submit
        binding.btnSubmit.setOnClickListener { onSubmit() }
    }


    @SuppressLint("SetTextI18n")
    private fun onSubmit() {
        if(selectedPosition==-1){
            currentPosition++
            if(currentPosition <= lenList){
                setQuestion()
                selectedPosition = 0
            }
            else {
                val intent = Intent(this, ResultActivity::class.java).setAction("your.custom.action")
                intent.putExtra("name", name)
                intent.putExtra("correctAnswer", correctAnswer)
                intent.putExtra("totalQuestion", lenList)
                startActivity(intent)
                finish()
            }
        }
        else if(selectedPosition==0){
            Toast.makeText(this, "Please choose your answer?", Toast.LENGTH_LONG).show()
        }
        else{
            val questionList = questionLists[currentPosition-1]
            if(questionList.correctAnswer != selectedPosition){
                answerView(selectedPosition, R.drawable.wrong_border)
            }
            else{
                correctAnswer++
            }
            answerView(questionList.correctAnswer, R.drawable.correct_border)
            if(currentPosition==lenList){
                binding.btnSubmit.text = "FINISH"
            }
            else{
                binding.btnSubmit.text = "GO TO THE NEXT QUESTION"
            }
            selectedPosition = -1
        }
    }

    //set background hien thi cau tra loi dung hoac sai len moi text view
    private fun answerView(selectedPosition: Int, bg: Int) {
        when(selectedPosition){
            1 -> {binding.tvOptionOne.setBackgroundResource(bg)}
            2 -> {binding.tvOptionTwo.setBackgroundResource(bg)}
            3 -> {binding.tvOptionThree.setBackgroundResource(bg)}
            4 -> {binding.tvOptionFour.setBackgroundResource(bg)}
        }
    }

    private fun selectAnswer(tv: TextView, position: Int) {
        defaultAnswer()
        selectedPosition = position
        tv.setTextColor(Color.parseColor("#FF00FF"))
        tv.setBackgroundResource(R.drawable.focused_border)
    }

    //ham do du lieu ra giao dien
    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        defaultAnswer()

        binding.progressBar.progress = currentPosition
        binding.tvMinMAx.text = "$currentPosition/$lenList"

        binding.tvQuestion.text = questionLists[currentPosition-1].question
        binding.imageView.setImageResource(questionLists[currentPosition-1].image)
        binding.tvOptionOne.text = questionLists[currentPosition-1].optionOne
        binding.tvOptionTwo.text = questionLists[currentPosition-1].optionTwo
        binding.tvOptionThree.text = questionLists[currentPosition-1].optionThree
        binding.tvOptionFour.text = questionLists[currentPosition-1].optionFour
        binding.btnSubmit.text = "SUBMIT"
    }

    //ham giup cho cac tv o trang thai bth, khi khong co su kien click len no
    private fun defaultAnswer() {
        val options = listOf(
            binding.tvOptionOne,
            binding.tvOptionTwo,
            binding.tvOptionThree,
            binding.tvOptionFour
        )

        options.forEach { option ->
            option.setTextColor(Color.parseColor("#888888"))
            option.setBackgroundResource(R.drawable.default_border)
        }

    }
}