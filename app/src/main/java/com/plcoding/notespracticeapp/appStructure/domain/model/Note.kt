package com.plcoding.notespracticeapp.appStructure.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.notespracticeapp.appStructure.presentation.ui.theme.BabyBlue
import com.plcoding.notespracticeapp.appStructure.presentation.ui.theme.LightGreen
import com.plcoding.notespracticeapp.appStructure.presentation.ui.theme.RedOrange
import com.plcoding.notespracticeapp.appStructure.presentation.ui.theme.RedPink
import com.plcoding.notespracticeapp.appStructure.presentation.ui.theme.Violet

@Entity(tableName = "note")
//features that any note would have
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val colour: Int, //colours are typically stored as ARGB ints in Android, dw about it;
    @PrimaryKey val id: Int? = null //must be nullable to store in room database
){
    companion object {
        val noteColours = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

//note validation message
class InvalidNoteException(message: String): Exception(message)
