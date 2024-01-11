package com.devemir.bihaber.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.devemir.bihaber.data.model.Articles

@Database(entities = [Articles::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao():NewsDao

    companion object {
        @Volatile private var instance :NewsDatabase?=null

        private val lock = Any()
        operator  fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance=it
            }
        }

       private fun createDatabase(context: Context)= databaseBuilder(context.applicationContext,NewsDatabase::class.java,"newsdatabase").build()
    }
}