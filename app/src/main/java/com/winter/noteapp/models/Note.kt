package com.winter.noteapp.models

import androidx.compose.runtime.mutableStateListOf

data class Note(val id: Int, var title: String, var description: String)

class NoteRepository  {
    private val notes = mutableStateListOf<Note>()

    fun getAllNotes() = notes

    fun addNote(title: String, description: String): Int {
        val id = when {
            notes.isEmpty() -> 1
            else -> notes.last().id + 1
        }
        notes.add(Note(
            id,
            title,
            description
        ))
        return id
    }

    fun updateNote(id: Int, newtitle: String, newDescription: String) {
        getNoteById(id)?.run {
            title = newtitle
            description = newDescription
        }
    }
    fun getNoteById(id: Int) =
        notes.find {
            it.id == id
        }

    fun deleteNoteById(id: Int) {
        notes.removeIf{ it.id == id }
    }
}