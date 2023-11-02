package com.winter.noteapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.winter.noteapp.components.NoteItem
import com.winter.noteapp.components.notesList
import com.winter.noteapp.models.AppRoutes
import com.winter.noteapp.models.NoteRepository
import com.winter.noteapp.ui.theme.MyColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAppOverview(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier,
        containerColor = MyColors.beige,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Notes",
                        style = TextStyle(
                            color = MyColors.codeBackground,
                            fontSize = 27.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MyColors.beige,
                    titleContentColor = MyColors.textDarkGray
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppRoutes.Create.route)
                },
                containerColor = Color.White,
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
                    items(notesList.getAllNotes()) { note -> 
                        NoteItem(note = note, navController = navController)
                    }
                }
            }
        }
    }
}
