package com.winter.noteapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.winter.noteapp.models.Note

@Composable
fun NoteItem(
    note: Note,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
            pressedElevation = 16.dp
        ),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("edit/${note.id}")
            },
        content = {
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
    )
}