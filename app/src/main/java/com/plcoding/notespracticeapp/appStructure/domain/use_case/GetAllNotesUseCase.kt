package com.plcoding.notespracticeapp.appStructure.domain.use_case

import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.domain.repository.NoteRepository
import com.plcoding.notespracticeapp.appStructure.domain.util.NoteOrder
import com.plcoding.notespracticeapp.appStructure.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//add injection constructor
class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    //overriding invoke w/ operator allows class to be called like a function
    operator fun invoke(
        //default order of notes will be by date
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        //business logic for getting and ordering notes
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Colour-> notes.sortedBy { it.colour }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Colour-> notes.sortedByDescending { it.colour }
                    }
                }
            }
        }
    }
}