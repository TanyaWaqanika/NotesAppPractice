package com.plcoding.notespracticeapp.appStructure.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.notespracticeapp.appStructure.domain.model.Note

@Database(
    entities = [Note::class], // define tables present in db
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    // creates datebase name
    companion object {
        const val DATEBASE_NAME = "notes_db"
    }
}