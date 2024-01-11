package com.devemir.bihaber.di

import android.app.Application
import androidx.room.Room
import com.devemir.bihaber.data.datasource.RemoteNewsDataSource
import com.devemir.bihaber.data.datasource.RemoteNewsDataSourceImpl
import com.devemir.bihaber.db.NewsDao
import com.devemir.bihaber.db.NewsDatabase
import com.devemir.bihaber.local.data_source.NewsLocalDataSource
import com.devemir.bihaber.local.data_source.NewsLocalDataSourceImpl
import com.devemir.bihaber.remote.NewsApiService
import com.devemir.bihaber.repository.NewsRepository
import com.devemir.bihaber.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesApiRemoteDataSource(newsApiService: NewsApiService):RemoteNewsDataSource{
        return RemoteNewsDataSourceImpl(newsApiService)
    }
   @Provides
   @Singleton
   fun providesApiLocalDataSource(newsDao: NewsDao):NewsLocalDataSource{
       return NewsLocalDataSourceImpl(newsDao)
   }

    @Provides
    @Singleton
    fun providesApiRemoteRepository(remoteNewsDataSource: RemoteNewsDataSource,newsLocalDataSource: NewsLocalDataSource):NewsRepository{
        return NewsRepositoryImpl(remoteNewsDataSource,newsLocalDataSource)
    }
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(
            application,
            NewsDatabase::class.java,
            "bihaber"
        ).build()
    }


    @Provides
    @Singleton
    fun provideArticleDAO(appDatabase: NewsDatabase) = appDatabase.newsDao()


}