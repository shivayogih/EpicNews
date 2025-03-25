package com.shivayogi.epicnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shivayogi.epicnews.data.models.NewsArticle

@Database(entities = [NewsArticle::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}