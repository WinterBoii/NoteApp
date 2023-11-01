package com.winter.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.winter.noteapp.models.AppRoutes
import com.winter.noteapp.screens.NoteAppOverview
import com.winter.noteapp.screens.NoteEditScreen

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
