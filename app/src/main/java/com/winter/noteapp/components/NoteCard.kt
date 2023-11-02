package com.winter.noteapp.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.winter.noteapp.models.AppRoutes
import com.winter.noteapp.models.Note
import com.winter.noteapp.models.NoteRepository
import com.winter.noteapp.ui.theme.MyColors

@Composable
fun NoteItem(
    note: Note,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var expand by remember { mutableStateOf(false) }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .windowInsetsEndWidth(insets = WindowInsets(right = 250.dp)),

            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    IconButton(onClick = {
                        expand = !expand },
                        modifier = modifier
                            .padding(top = 5.dp),
                        ) {
                        Icon(
                            if (expand) Icons.Filled.KeyboardArrowDown
                            else Icons.Filled.KeyboardArrowRight, "",
                            modifier = Modifier
                                .padding(6.dp)
                        )
                    }
                }
                if (expand)
                    Text(
                        text = note.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
            }

            Column(
                modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = {
                        println(AppRoutes.Edit.route + "${note.id}")
                        navController.navigate("${AppRoutes.Edit.route}/${note.id}")
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MyColors.codeBackground,
                        contentColor = MyColors.beige
                    ),
                    modifier = modifier
                        .clip(CircleShape)
                ) {
                    Icon(Icons.Filled.Edit, "Edit")
                }
                IconButton(
                    onClick = {
                        showDialog = true
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MyColors.codeBackground,
                        contentColor = MyColors.beige
                    ),
                    modifier = modifier
                        .clip(CircleShape)
                ) {
                    Icon(Icons.Filled.Delete, "Delete")
                }
            }
        }
        if (showDialog) {
            ConfirmDeleteDialog(
                onConfirm = {
                    notesList.deleteNoteById(note.id)
                    showDialog = false
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }
    }
}