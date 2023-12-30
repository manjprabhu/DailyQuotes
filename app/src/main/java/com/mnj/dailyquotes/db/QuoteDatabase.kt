package com.mnj.dailyquotes.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [QuoteEntity::class]
)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun getDao(): QuoteDao
}