package com.example.b2013527_thbuoi3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.b2013527_thbuoi3.obj.Constants.NEWS_TABLE

@Entity(tableName = NEWS_TABLE)
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val newsId :Int,
    @ColumnInfo(name = "news_title")
    val newsTitle:String,
    @ColumnInfo(name = "news_desc")
    val newsDesc : String
)