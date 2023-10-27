package com.winter.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteApp()
        }
    }
}

@Composable
fun NoteApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        composable("start") {
            NoteAppOverview(navController)
        }
        composable("create") {
            NoteEditScreen(navController)
        }
        composable("edit/{id}") {
            val id = it.arguments!!.getString("id")!!.toInt()
            NoteEditScreen(navController, noteId = id)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAppOverview(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text("Notes")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("create")
                },
                content = { Icon(Icons.Filled.Add, " ") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (listOf(notesList).isEmpty()) {
                Text("No notes yet!")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(
                        items = notesList.getAllNotes(),
                        key = { note -> note.id },
                        itemContent = { note ->

                            NoteItem(note = note, navController)
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("edit/${note.id}")
            }
    ) {
        Column(
            modifier.padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    navController: NavController,
    noteId: Int? = null,
    modifier: Modifier = Modifier
) {
    val note = noteId?.let { notesList.getNoteById(it) }

    var titleTextState by remember { mutableStateOf(note?.title ?: "") }
    var descriptionTextState by remember { mutableStateOf(note?.description ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (note == null) "New Note"
                        else "Edit Note"
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "Back arrow")
                    }
                }
            )
        },

    ) { padding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(padding),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = titleTextState,
                onValueChange = { titleTextState = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 13.dp)
            )
            TextField(
                value = descriptionTextState,
                onValueChange = { descriptionTextState = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 13.dp)
            )
            Button(
                modifier = Modifier
                    .padding(all = 13.dp)
                    .align(CenterHorizontally),
                onClick = { 
                    if (note == null) {
                        notesList.addNote(
                            title = titleTextState,
                            description = descriptionTextState
                        )
                    } else {
                        notesList.updateNote(
                            noteId,
                            titleTextState,
                            descriptionTextState
                        )
                    }
                    navController.popBackStack()
                },
                enabled = titleTextState.isNotBlank(),
                content = {
                    Text(
                        if (note == null) "Create"
                        else "Edit")
                }
            )
        }
    }
}

// Global variable to store the notes
val notesList = NoteRepository().apply {
    addNote("Sushi", "is Beyond.")
    addNote("Sushi", "is Amazingness")
}

data class Note(val id: Int, var title: String, var description: String)

class NoteRepository  {
    private val notes = mutableListOf<Note>()

    fun getAllNotes() = notes

    fun addNote(title: String, description: String) {
        val id = when {
            notes.isEmpty() -> 1
            else -> notes.last().id + 1
        }
        notes.add(Note(
            id,
            title,
            description
        ))
        //return id
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

