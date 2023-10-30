package com.example.b2013527_duonghongdoan_uocmo.models

import com.google.gson.annotations.SerializedName

data class RequestAddWish(
    val idUser: String,
    @SerializedName("name")//dung de doi dung ten tham so truyen len server
    val fullName: String,
    val content: String
)
