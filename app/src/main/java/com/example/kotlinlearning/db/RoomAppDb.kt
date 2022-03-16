package com.example.kotlinlearning.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [NewsEntity::class], version = 1)
abstract class RoomAppDb: RoomDatabase() {

    abstract fun userDao(): Dao?

    companion object {
        private var INSTANCE: RoomAppDb?= null
        fun getAppDatabase(context: Context): RoomAppDb? {
            if(INSTANCE == null ) {

                INSTANCE = Room.databaseBuilder<RoomAppDb>(
                    context.applicationContext, RoomAppDb::class.java, "HEADLINE_NEWS"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}
