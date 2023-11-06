package com.example.b2013527_duonghongdoan_mymap.models

import java.io.Serializable

data class UserMap(
    val title: String,
    val places: List<Place>
):Serializable //truyen du lieu class qua intent
