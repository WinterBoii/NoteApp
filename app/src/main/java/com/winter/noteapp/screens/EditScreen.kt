package com.winter.noteapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.winter.noteapp.components.notesList
import com.winter.noteapp.ui.theme.MyColors

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
    var isError by remember { mutableStateOf(false)}

    Scaffold(
        containerColor = MyColors.beige,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (note == null) "New Note"
                        else "Edit Note",
                        style = TextStyle(
                            color = MyColors.codeBackground,
                            fontSize = 27.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MyColors.beige
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
            OutlinedTextField(
                value = titleTextState,
                onValueChange = {
                    titleTextState = it
                    isError = titleTextState.isBlank() || (titleTextState.length < 3)
                },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 13.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedBorderColor = MyColors.codeBackground,
                    focusedLabelColor = MyColors.codeBackground
                ),
                isError = isError
            )
            OutlinedTextField(
                value = descriptionTextState,
                onValueChange = {
                    descriptionTextState = it
                    isError = descriptionTextState.isBlank() || (descriptionTextState.length < 1)
                },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 13.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedBorderColor = MyColors.codeBackground,
                    focusedLabelColor = MyColors.codeBackground
                ),
                isError = isError
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
                enabled = !isError,
                content = {
                    Text(
                        if (note == null) "Create"
                        else "Edit")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MyColors.codeBackground,
                    disabledContainerColor = MyColors.textGray,
                    disabledContentColor = MyColors.beige
                )
            )
        }
    }
}
