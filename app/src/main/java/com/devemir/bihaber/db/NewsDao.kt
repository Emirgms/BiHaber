package com.devemir.bihaber.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devemir.bihaber.data.model.Articles
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg news: Articles)

    @Query("Select * From Articles")
    fun getAllNews(): Flow<List<Articles>>

    @Query("Delete From Articles Where url= :articleUrl")
      fun deleteSaveNews(articleUrl: String):Int

     @Query("Select EXISTS (Select * From Articles Where url= :articleUrl)")
      fun isBookmarked(articleUrl:String):Boolean
}