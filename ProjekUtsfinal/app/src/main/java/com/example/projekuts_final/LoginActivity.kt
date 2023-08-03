package com.example.projekuts_final

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHandler
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHandler(this)
        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val user = dbHelper.getUser(username)
            if (user != null && user.password == password) {
                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
//                val intentData = Intent(this, HomeFragment::class.java)


//                val intent = Intent (this, MainActivity::class.java )
//                intent.putExtra("USERNAME", user.username)
//                startActivity(intent)
//                finish()
//                val bundle = Bundle().apply {
//                    putString("USERNAME_KEY", username)
//                }
//
//                val fragment = AkunFragment().apply {
//                    arguments = bundle
//                }
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USERNAME_KEY", username)
                }


//
//                val intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("name", username)
//                val bundle = Bundle()
//                bundle.putString("username", username)
//                intent.putExtras(bundle)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
            }
        }


        val registerButton = findViewById<TextView>(R.id.textViewRegister)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegistrasiActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }


}