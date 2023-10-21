package com.example.b2013527_calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    private lateinit var tvInput: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //khai bao cac id
        tvInput = findViewById(R.id.tvInput)

    }

    @SuppressLint("SetTextI18n")
    fun onDigit(view: View) {
        var text = view.findViewById<Button>(view.id).text.toString()
        tvInput.text = tvInput.text.toString() + text
    }

    fun onClear(view: View) {
        var len = tvInput.text.length
        if(len > 0) {
            tvInput.text = tvInput.text.substring(0, len-1)
        }
    }

    @SuppressLint("SetTextI18n")
    fun onDecimalPoint(view: View) {
        var dot = view.findViewById<Button>(view.id).text.toString()
        tvInput.text = tvInput.text.toString() + dot
    }

    @SuppressLint("SetTextI18n")
    fun onOperator(view: View) {
        var operator = view.findViewById<Button>(view.id).text.toString()
        tvInput.text = tvInput.text.toString() + operator
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        var noidung = tvInput.text.toString()
        var kq = 0.0
        if("+" in noidung) {
            var arr = noidung.split("+")
            kq = (arr[0].toDouble() + arr[1].toDouble())
        } else if("-" in noidung && noidung.first().toString() != "-") {
            var arr = noidung.split("-")
            kq = (arr[0].toDouble() - arr[1].toDouble())
        } else if("*" in noidung) {
            var arr = noidung.split("*")
            kq = (arr[0].toDouble() * arr[1].toDouble())
        }else if("/" in noidung) {
            var arr = noidung.split("/")
            kq = (arr[0].toDouble() / arr[1].toDouble())
        }
        tvInput.text = removeZeroAfterDot(kq.toString())
    }

    private fun removeZeroAfterDot(result: String): String {
        if("." in result){
            var arr = result.split(".")
            if(arr[1].toString().length==1 && arr[1].toInt()==0) {
                return arr[0]
            }
        }
        return result
    }
}