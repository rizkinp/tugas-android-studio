package com.example.projekuts

data class Transaction(
    val id: Int,
    val fromAccount: Int,
    val toAccount: Int,
    val balance: Double,
    val timestamp: String
)
