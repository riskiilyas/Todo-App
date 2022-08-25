package com.keecoding.todoapp.presentation.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.keecoding.todoapp.data.db.AppDB
import com.keecoding.todoapp.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideDB(app: Application): AppDB {
        return Room.databaseBuilder(
            app, AppDB::class.java, Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(appDB: AppDB) = appDB.todoDao()
}