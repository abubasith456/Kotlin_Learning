package com.example.kotlinlearning.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [News::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): Dao?
    private var INSTANCE: AppDatabase? = null

    open fun getDbInstance(context: Context): AppDatabase? {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "OFFLINE_NEWS"
            )
                .allowMainThreadQueries()
                .build()
        }
        return INSTANCE
    }

}
