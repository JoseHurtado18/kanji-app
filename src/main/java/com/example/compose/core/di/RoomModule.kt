package com.example.compose.core.di

import android.content.Context
import androidx.room.Room
import com.example.compose.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideKanjiDAO(db: AppDatabase) = db.getKanjiDAO()

    @Singleton
    @Provides
    fun provideExampleWordDAO(db: AppDatabase) = db.getExampleWordDAO()
}