package com.plcoding.notespracticeapp.di

import com.plcoding.notespracticeapp.appStructure.domain.repository.NoteRepository
import com.plcoding.notespracticeapp.appStructure.domain.use_case.AddNoteUseCase
import com.plcoding.notespracticeapp.appStructure.domain.use_case.DeleteNoteUseCase
import com.plcoding.notespracticeapp.appStructure.domain.use_case.GetAllNotesUseCase
import com.plcoding.notespracticeapp.appStructure.domain.use_case.GetNoteUseCase
import com.plcoding.notespracticeapp.appStructure.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
    return NoteUseCases(
        getAllNotesUseCase = GetAllNotesUseCase(repository),
        deleteNoteUseCase = DeleteNoteUseCase(repository),
        addNoteUseCase = AddNoteUseCase(repository),
        getNoteUseCase = GetNoteUseCase(repository)
    )
    }
}