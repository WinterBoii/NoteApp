package com.winter.noteapp.components

import com.winter.noteapp.models.NoteRepository

// Global variable to store the notes
val notesList = NoteRepository().apply {
    addNote("Sushi", "is Del.")
    addNote("Sushi", "is sweet")
}