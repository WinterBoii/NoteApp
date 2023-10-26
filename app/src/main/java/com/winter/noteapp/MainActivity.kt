package com.winter.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = notesList.getAllNotes(),
                        key = { note -> note.id },
                        itemContent = { note ->

                            NoteItem(note = note)
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
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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
    noteId: Int? = null
) {
    val note = noteId?.let { notesList.getNoteById(it) }

    val titleTextState = remember { mutableStateOf(note?.title ?: "") }
    val descriptionTextState = remember { mutableStateOf(note?.title ?: "") }

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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = titleTextState.value,
                onValueChange = { titleTextState.value = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 13.dp)
            )
            TextField(
                value = descriptionTextState.value,
                onValueChange = { descriptionTextState.value = it },
                label = { Text("Desc") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 13.dp)
            )
            Button(
                modifier = Modifier
                    .padding(all = 13.dp)
                    .align(CenterHorizontally),
                onClick = { 
                    notesList.addNote(
                        title = titleTextState.value,
                        description = descriptionTextState.value
                    )
                    navController.popBackStack()
                }
            ) {
                Text("Create")
            }
        }
    }
}

// Global variable to store the notes
val notesList = NoteRepository().apply {
    addNote("Shukri", "is Beautiful.")
    addNote("Shukri", "is Amaaaaaazing")
}

data class Note(val id: Int, val title: String, val description: String)

class NoteRepository  {
    private val notes = mutableListOf<Note>()

    fun getAllNotes() = notes

    fun getNoteById(id: Int) =
        notes.find {
            it.id == id
        }
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
}

