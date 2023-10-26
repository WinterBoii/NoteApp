package com.winter.noteapp

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppOverview()
        }
    }
}
// Global variable to store the notes
val notes = mutableStateListOf<Note>()

class Note(val title: String, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAppOverview() {
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
                onClick = { /* Navigate to the note creation screen */ },
                content = { Icon(Icons.Filled.Add, "") }
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
            /*if (notes.isEmpty()) {
                Text(text = "No notes yet!")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(notes) {note ->
                        NoteItem(note)
                    }
                }
            }*/
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(3) {i ->
                        NoteItem(note = Note("Title", "Description"))
                    }
                }
            )
        }
    }
}

@Composable
fun NoteItem(note: Note) {
    ElevatedCard(
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
fun NoteCreateScreen(note: Note? = null) {
    val titleTextState = remember { mutableStateOf(note?.title ?: "") }
    val titledescriptionState = remember { mutableStateOf(note?.title ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (note == null) "Create New Note"
                        else "Edit Note"
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Save the note */ },
                content = { Icon(Icons.Filled.Add, "") }
            )
        },

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                value = titledescriptionState.value,
                onValueChange = { titledescriptionState.value = it },
                label = { Text("Desc") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 13.dp)
            )
        }
    }
}

//@Preview
@Composable
fun NotesOverviewScreenPreview() {
    NoteAppOverview()
}

//@Preview
@Composable
fun NotesCreateScreenPreview() {
    NoteCreateScreen()
}