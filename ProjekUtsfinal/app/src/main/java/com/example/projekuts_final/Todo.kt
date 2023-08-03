package com.example.projekuts_final

data class Todo(
    var id: Int,
    var topic: String,
    var description: String,
    var date: String,
    var time: String,
    val completedDate: String,
    val status: String
)