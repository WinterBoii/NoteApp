package com.winter.noteapp.models

sealed class AppRoutes(val route: String) {
    object Start : AppRoutes("start")
    object Create : AppRoutes("create")
    data class Edit(val id: Int) : AppRoutes("edit/{id}")
}