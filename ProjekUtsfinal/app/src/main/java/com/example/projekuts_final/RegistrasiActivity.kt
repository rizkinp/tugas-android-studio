package com.example.projekuts_final

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrasiActivity : AppCompatActivity() {

    private val dbHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)

        val myButton = findViewById<Button>(R.id.my_button)
        val etName = findViewById<EditText>(R.id.editTextName)
        val etUsername = findViewById<EditText>(R.id.editTextUsername)
        val etemail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)
        val radioButtonMale = findViewById<RadioButton>(R.id.radioButtonMale)
        val spinnerStatus = findViewById<Spinner>(R.id.spinnerStatus)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        // populate spinner with status options
        val statusList = ArrayList<String>()
        statusList.add("SD")
        statusList.add("SMP")
        statusList.add("SMA")
        statusList.add("Mahasiswa")
        statusList.add("Pekerja")
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusList)
        spinnerStatus.adapter = statusAdapter

        myButton.setBackgroundResource(android.R.color.transparent)
        myButton.setOnClickListener {
            // aksi yang ingin dilakukan ketika button di klik
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // set button click listener to register user
        buttonRegister.setOnClickListener {
            val name = etName.text.toString()
            val username = etUsername.text.toString()
            val email = etemail.text.toString()
            val password = etPassword.text.toString()
            val gender = if (radioButtonMale.isChecked) "Laki-laki" else "Perempuan"
            val status = spinnerStatus.selectedItem.toString()

            // insert user data to database
            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    dbHandler.insertUser(User(name, username, email, password, gender, status))
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Registrasi gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}