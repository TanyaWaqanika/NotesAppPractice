package com.plcoding.notespracticeapp.appStructure

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// hilt injection here,
@HiltAndroidApp
class NoteApp: Application() {
}