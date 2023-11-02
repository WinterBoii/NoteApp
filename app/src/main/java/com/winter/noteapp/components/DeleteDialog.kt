package com.winter.noteapp.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.winter.noteapp.ui.theme.MyColors

@Composable
fun ConfirmDeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Confirm Delete") },
        text = { Text("Are you sure you want to delete this item?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes", color = MyColors.codeBackground)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No", color = MyColors.codeBackground)
            }
        },
        containerColor = MyColors.beige,
        tonalElevation = 20.dp
    )
}