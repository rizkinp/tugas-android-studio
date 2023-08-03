package com.example.myapplication



import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var etNim: EditText
    private lateinit var etNama: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etGender: EditText
    private lateinit var btnSelectImage: Button
    private lateinit var btnInsert: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnSearch: Button
    private lateinit var imageView: ImageView

    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNim = findViewById(R.id.etNim)
        etNama = findViewById(R.id.etNama)
        etAlamat = findViewById(R.id.etAlamat)
        etGender = findViewById(R.id.etGender)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        btnInsert = findViewById(R.id.btnInsert)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        btnSearch = findViewById(R.id.btnSearch)
        imageView = findViewById(R.id.imageView)

        btnSelectImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PICK_IMAGE_REQUEST
                )
            } else {
                openGallery()
            }
        }

        btnInsert.setOnClickListener {
            val nim = etNim.text.toString().trim()
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val gender = etGender.text.toString().trim()

            if (nim.isNotEmpty() && nama.isNotEmpty() && alamat.isNotEmpty() && gender.isNotEmpty()) {
                if (imageUri != null) {
                    insertDataWithImage(nim, nama, alamat, gender, imageUri!!)
                } else {
                    insertData(nim, nama, alamat, gender)
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdate.setOnClickListener {
            val nim = etNim.text.toString().trim()
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val gender = etGender.text.toString().trim()

            if (nim.isNotEmpty() && nama.isNotEmpty() && alamat.isNotEmpty() && gender.isNotEmpty()) {
                updateData(nim, nama, alamat, gender)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            val nim = etNim.text.toString().trim()
            deleteData(nim)
        }

        btnSearch.setOnClickListener {
            val nim = etNim.text.toString().trim()
            searchData(nim)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PICK_IMAGE_REQUEST && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImage = data.data
            imageUri = selectedImage.toString()
            imageView.setImageURI(selectedImage)
        }
    }

    private fun insertData(nim: String, nama: String, alamat: String, gender: String) {
        val client = OkHttpClient()
        val url = "http://your_php_api_url/insert_data.php"

        val formBody = FormBody.Builder()
            .add("nim", nim)
            .add("nama", nama)
            .add("alamat", alamat)
            .add("gender", gender)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Failed to insert data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                val jsonObject = JSONObject(responseData)
                val success = jsonObject.getInt("success")
                val message = jsonObject.getString("message")
                runOnUiThread {
                    if (success == 1) {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        clearFields()
                    } else {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun insertDataWithImage(
        nim: String,
        nama: String,
        alamat: String,
        gender: String,
        imageUri: String
    ) {
        val client = OkHttpClient()
        val url = "http://your_php_api_url/insert_data_with_image.php"

        val formBody = FormBody.Builder()
            .add("nim", nim)
            .add("nama", nama)
            .add("alamat", alamat)
            .add("gender", gender)
            .add("image_uri", imageUri)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Failed to insert data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                val jsonObject = JSONObject(responseData)
                val success = jsonObject.getInt("success")
                val message = jsonObject.getString("message")
                runOnUiThread {
                    if (success == 1) {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        clearFields()
                    } else {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun updateData(nim: String, nama: String, alamat: String, gender: String) {
        val client = OkHttpClient()
        val url = "http://your_php_api_url/update_data.php"

        val formBody = FormBody.Builder()
            .add("nim", nim)
            .add("nama", nama)
            .add("alamat", alamat)
            .add("gender", gender)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Failed to update data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                val jsonObject = JSONObject(responseData)
                val success = jsonObject.getInt("success")
                val message = jsonObject.getString("message")
                runOnUiThread {
                    if (success == 1) {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        clearFields()
                    } else {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun deleteData(nim: String) {
        val client = OkHttpClient()
        val url = "http://your_php_api_url/delete_data.php"

        val formBody = FormBody.Builder()
            .add("nim", nim)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Failed to delete data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                val jsonObject = JSONObject(responseData)
                val success = jsonObject.getInt("success")
                val message = jsonObject.getString("message")
                runOnUiThread {
                    if (success == 1) {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        clearFields()
                    } else {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun searchData(nim: String) {
        val client = OkHttpClient()
        val url = "http://your_php_api_url/search_data.php?nim=$nim"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Failed to fetch data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                val jsonObject = JSONObject(responseData)
                val success = jsonObject.getInt("success")
                val message = jsonObject.getString("message")

                runOnUiThread {
                    if (success == 1) {
                        val data = jsonObject.getJSONArray("data")
                        if (data.length() > 0) {
                            val student = data.getJSONObject(0)
                            val nama = student.getString("nama")
                            val alamat = student.getString("alamat")
                            val gender = student.getString("gender")

                            etNama.setText(nama)
                            etAlamat.setText(alamat)
                            etGender.setText(gender)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Data not found",
                                Toast.LENGTH_SHORT
                            ).show()
                            clearFields()
                        }
                    } else {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        clearFields()
                    }
                }
            }
        })
    }

    private fun clearFields() {
        etNim.text.clear()
        etNama.text.clear()
        etAlamat.text.clear()
        etGender.text.clear()
        imageView.setImageResource(0)
        imageUri = null
    }
}



