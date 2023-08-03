package com.example.projectutstodolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            // Validasi username dan password pada database
            val dbHelper = TodoDatabaseHelper(this)
            val currentUser = dbHelper.getUserByUsernameAndPassword(username, password)

            if (currentUser != null) {
                // Jika username dan password sudah sesuai, buka halaman Home
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Jika tidak, tampilkan pesan error atau kosongkan field username dan password
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                editTextUsername.setText("")
                editTextPassword.setText("")
            }
        }
    }
}
