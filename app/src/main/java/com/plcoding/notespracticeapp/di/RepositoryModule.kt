package com.plcoding.notespracticeapp.di

import com.plcoding.notespracticeapp.appStructure.data.data_source.NoteDatabase
import com.plcoding.notespracticeapp.appStructure.data.repository.NoteRepositoryImpl
import com.plcoding.notespracticeapp.appStructure.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }
}
