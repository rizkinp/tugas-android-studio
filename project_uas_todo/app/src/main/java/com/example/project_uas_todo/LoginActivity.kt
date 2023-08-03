package com.example.project_uas_todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            login(username, password)
        }
    }

    private fun login(username: String, password: String) {
        val url = "http://192.168.60.64/project_uas_todo/public/api/users"
        val request = JSONObject()
        request.put("username", username)
        request.put("password", password)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, request,
            { response ->
                try {
                    val success = response.getBoolean("success")

                    if (success) {
                        // Login berhasil
                        val token = response.getString("token")
                        val userId = response.getString("userId")
                        val name = response.getString("name")

                        // Simpan informasi login di sini (misalnya, di SharedPreferences)

                        // Navigasi ke halaman berikutnya setelah login berhasil
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Login gagal
                        val message = response.getString("message")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                Log.e("LoginActivity", "Error: ${error.message}")
                Toast.makeText(this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }
}
