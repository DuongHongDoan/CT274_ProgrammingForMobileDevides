package com.example.b2013527_thbuoi3.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.b2013527_thbuoi3.obj.Constants.NEWS_TABLE

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(newsEntity: NewsEntity)
    @Update
    fun updateNews(newsEntity: NewsEntity)
    @Delete
    fun deleteNews(newsEntity: NewsEntity)
    @Query("SELECT * FROM $NEWS_TABLE ORDER BY newsId DESC")
    fun getAllNews() : MutableList<NewsEntity>
    @Query("SELECT * FROM $NEWS_TABLE WHERE newsId LIKE :id")
    fun getNews(id : Int) : NewsEntity
}