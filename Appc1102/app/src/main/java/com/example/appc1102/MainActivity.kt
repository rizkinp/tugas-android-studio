package com.example.appc1102

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 200
    private val PICK_IMAGE_REQUEST = 100
    private lateinit var selectedImageUri: Uri

    private val url = "http://your_php_api_url/"
    private lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi OkHttpClient
        client = OkHttpClient()

        // Mengisi dropdown jenis kelamin
        val genderList = listOf("Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_gender.adapter = adapter

        // Meminta izin READ_EXTERNAL_STORAGE jika belum diberikan
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }

        // Mengatur onClickListener untuk tombol-tombol
        btn_choose_image.setOnClickListener { openGallery() }
        btn_insert.setOnClickListener { insertData() }
        btn_update.setOnClickListener { updateData() }
        btn_delete.setOnClickListener { deleteData() }
        btn_search.setOnClickListener { searchData() }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun insertData() {
        val nim = et_nim.text.toString()
        val nama = et_nama.text.toString()
        val alamat = et_alamat.text.toString()
        val gender = spinner_gender.selectedItem.toString()

        if (nim.isNotEmpty() && nama.isNotEmpty() && alamat.isNotEmpty() && ::selectedImageUri.isInitialized) {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nim", nim)
                .addFormDataPart("nama", nama)
                .addFormDataPart("alamat", alamat)
                .addFormDataPart("jenis_kelamin", gender)
                .addFormDataPart("gambar", "image.jpg", getRequestBody())
                .build()

            val request = Request.Builder()
                .url(url + "insert_data_with_image.php")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    runOnUiThread {
                        try {
                            val json = JSONObject(responseData)
                            val success = json.getInt("success")
                            val message = json.getString("message")
                            if (success == 1) {
                                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                                clearFields()
                            } else {
                                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: JSONException) {
                            Toast.makeText(this@MainActivity, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        } else {
            Toast.makeText(this, "Please fill in all the fields and choose an image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData() {
        // Implementasi kode untuk update data ke server PHP
        // Sesuaikan dengan kebutuhan Anda
    }

    private fun deleteData() {
        // Implementasi kode untuk hapus data di server PHP
        // Sesuaikan dengan kebutuhan Anda
    }

    private fun searchData() {
        // Implementasi kode untuk mencari data di server PHP
        // Sesuaikan dengan kebutuhan Anda
    }

    private fun getRequestBody(): RequestBody {
        val bitmap = (iv_image.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageByteArray = byteArrayOutputStream.toByteArray()
        return RequestBody.create(MediaType.parse("image/jpeg"), imageByteArray)
    }

    private fun clearFields() {
        et_nim.text.clear()
        et_nama.text.clear()
        et_alamat.text.clear()
        iv_image.setImageDrawable(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data!!
            Glide.with(this)
                .load(selectedImageUri)
                .into(iv_image)
        }
    }
}
