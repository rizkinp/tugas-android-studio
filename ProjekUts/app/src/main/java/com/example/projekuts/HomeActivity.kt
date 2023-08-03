package com.example.projekuts

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHandler
    private lateinit var preferences: SharedPreferences
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        db = DatabaseHandler(this)
        preferences = getSharedPreferences("BankingApp", Context.MODE_PRIVATE)

//        user = db.getUserById(preferences.getInt("userId", -1))

        val balance_text_view: TextView = findViewById(R.id.balance_text_view)
        balance_text_view.text = "Balance: ${user.balance}"

        val transfer_button: Button = findViewById(R.id.transfer_button)
        transfer_button.setOnClickListener {
//            startActivity(Intent(this, TransferActivity::class.java))
        }
    }
}