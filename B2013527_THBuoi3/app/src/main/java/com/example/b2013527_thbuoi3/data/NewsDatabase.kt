package com.example.b2013527_thbuoi3.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun doa():NewsDao
}