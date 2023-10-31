package com.winter.noteapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.winter.noteapp.components.notesList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    navController: NavController,
    noteId: Int? = null,
    modifier: Modifier = Modifier,
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
                    .align(Alignment.CenterHorizontally),
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
