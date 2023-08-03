package com.example.projectutstodolist

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: String,
    val dueTime: String,
    val isComplete: Boolean
)
