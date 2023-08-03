package com.example.appc06_sql_login



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = DBHelper(this)

        val btn_login = findViewById<Button>(R.id.btn_login)
        val username = findViewById<TextView>(R.id.et_username)
        val password = findViewById<TextView>(R.id.et_password)
        btn_login.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()

            if (db.getUser(username, password)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



