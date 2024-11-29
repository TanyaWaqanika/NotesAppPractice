package com.plcoding.notespracticeapp.di

import android.content.Context
import androidx.room.Room
import com.plcoding.notespracticeapp.appStructure.data.data_source.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    // creates database for notes
    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.DATEBASE_NAME
        ).build()
    }
}