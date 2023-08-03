package com.example.projetutstodolist05

data class History(
    val id: Int,
    val topic: String,
    val description: String,
    val date: String,
    val time: String,
    val completedDate: String,
    val status: String
)