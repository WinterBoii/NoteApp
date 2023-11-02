package com.winter.noteapp.models

sealed class AppRoutes(val route: String) {
    object Start : AppRoutes("start")
    object Create : AppRoutes("create")
    object Edit: AppRoutes("edit")
}