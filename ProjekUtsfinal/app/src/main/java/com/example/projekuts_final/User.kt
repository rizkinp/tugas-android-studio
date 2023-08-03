package com.example.projekuts_final

//class User(val name: String, val username: String, val email: String, val password: String, val gender: String, val status: String) {
//
//    override fun toString(): String {
//        return "Name: $name\nusername: $username\nEmail: $email\nPassword: $password\nGender: $gender\nStatus: $status"
//    }
//}

data class User(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val gender: String,
    val status: String
)
