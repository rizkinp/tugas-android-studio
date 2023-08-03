package com.example.projekuts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHandler
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)

        db = DatabaseHandler(this)
        preferences = getSharedPreferences("BankingApp", Context.MODE_PRIVATE)

        if (preferences.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        val login_button: Button = findViewById(R.id.login_button)
        login_button.setOnClickListener {
            val username_edit_text: EditText = findViewById(R.id.username_edit_text)
            val password_edit_text: EditText = findViewById(R.id.password_edit_text)

            val name = username_edit_text.text.toString()
            val password = password_edit_text.text.toString()
            val user = db.getUserByName(name)
            if (user != null && user.password == password) {
                val editor = preferences.edit()
                editor.putInt("userId", user.id)
                editor.putBoolean("isLoggedIn", true)
                editor.apply()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
