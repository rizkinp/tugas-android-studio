
package com.example.appcdb

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appcdb.databinding.ActivityMainBinding
import com.android.volley.Request
import com.android.volley.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var b: ActivityMainBinding
    private lateinit var mediaHelper: MediaHelper
    private lateinit var mhsAdapter: AdapterDataMhs
    private lateinit var prodiAdapter: ArrayAdapter<String>
    private var daftarMhs = mutableListOf<HashMap<String,String>>()
    val daftarProdi = mutableListOf<String>()
    private var urlRoot = "http://192.168.60.64" // IP laptop
    private var url = "$urlRoot/mobile_laravel03/public/api/siakad"
    private var url2 = "$urlRoot/mobile_laravel03/public/api/prodi"
    private var imStr = ""
    private var pilihProdi = ""
    private val RC_CAMERA_PERMISSION = 1001
    private val RC_CAMERA_CAPTURE = 1002
    private val RC_GALLERY = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        mhsAdapter = AdapterDataMhs(daftarMhs, this, b)
        mediaHelper = MediaHelper(this)
        b.listMhs.layoutManager = LinearLayoutManager(this)
        b.listMhs.adapter = mhsAdapter

        prodiAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, daftarProdi)
        b.spinProdi.adapter = prodiAdapter

        b.spinProdi.onItemSelectedListener = itemSelected

        b.imUpload.setOnClickListener(this)
        b.btnUpdate.setOnClickListener(this)
        b.btnInsert.setOnClickListener(this)
        b.btnDelete.setOnClickListener(this)
        b.btnFind.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        showDataMhs("")
        getNamaProdi()
    }

    private val itemSelected = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            pilihProdi = daftarProdi[p2]
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            b.spinProdi.setSelection(0)
            pilihProdi = daftarProdi[0]
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imUpload -> {
                showImagePickerDialog()
            }
            R.id.btnInsert -> {
                queryInsertUpdateDelete("insert")
//                showDataMhs("");
            }
            R.id.btnDelete -> {
                queryInsertUpdateDelete("delete")
            }
            R.id.btnUpdate -> {
                queryInsertUpdateDelete("update")
            }
            R.id.btnFind -> {
                showDataMhs(b.edNamaMhs.text.toString().trim())
            }
        }
    }

    private fun openImagePicker() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )
        ) {
            // Permission has been denied previously, show rationale dialog
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                RC_CAMERA_PERMISSION
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                RC_CAMERA_PERMISSION
            )
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RC_GALLERY)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RC_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(
                        this,
                        "Camera permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                mediaHelper.getRcCamera() -> {
                    // Mendapatkan path gambar dari MediaHelper
                    val imagePath = mediaHelper.getCaptureImagePath()
                    if (imagePath.isNotEmpty()) {
                        // Menampilkan gambar di ImageView
                        setImageViewFromPath(imagePath)
                        // Mengonversi gambar ke base64 dan mengirimkannya ke database
                        imStr = convertImageToBase64(imagePath)
                    }
                }
                mediaHelper.getRcGallery() -> {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        // Menampilkan gambar di ImageView
                        b.imUpload.setImageURI(imageUri)
                        // Mengonversi gambar ke base64 dan mengirimkannya ke database
                        imStr = mediaHelper.getBitmapToString(imageUri, b.imUpload)
                    }
                }
            }
        }
    }
    private fun setImageViewFromPath(imagePath: String) {
        val file = File(imagePath)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            b.imUpload.setImageBitmap(bitmap)
        }
    }
    private fun convertImageToBase64(imagePath: String): String {
        val file = File(imagePath)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            return mediaHelper.bitmapToString(bitmap)
        }
        return ""
    }

    private fun openCamera() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            dispatchTakePictureIntent()
        } else {
            requestCameraPermission()
        }
    }


    private fun showImagePickerDialog() {
        val options = arrayOf<CharSequence>("Ambil Foto", "Pilih dari Galeri")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih Gambar")
        builder.setItems(options) { dialog, item ->
            when (item) {
                0 -> {
                    openCamera()
                }
                1 -> {
                    openGallery()
                }
            }
            dialog.dismiss()
        }
        builder.show()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Membuat file sementara untuk menyimpan gambar
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Gagal membuat file
                ex.printStackTrace()
                null
            }
            // Melanjutkan jika file berhasil dibuat
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.example.appcdb.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, mediaHelper.getRcCamera())
            }
        }
    }
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Simpan path file untuk digunakan nanti
            mediaHelper.setCaptureImagePath(absolutePath)
        }
    }

    private fun dispatchPickImageIntent() {
        val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImageIntent, RC_CAMERA_CAPTURE)
    }

    private fun queryInsertUpdateDelete(mode: String) {
        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val error = jsonObject.getString("kode")
                when (error) {
                    "000" -> {
                        Toast.makeText(this, "Operasi berhasil!", Toast.LENGTH_LONG).show()
                        showDataMhs("")
                    }
                    "111" -> Toast.makeText(this, "Gagal!", Toast.LENGTH_LONG).show()
                    "222" -> Toast.makeText(
                        this,
                        "Operasi Upload gagal!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this,
                    "Tidak dapat terhubung ke server",
                    Toast.LENGTH_LONG
                ).show()
                imStr = ""
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                val nmFile =
                    "DC" + SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date()) + ".jpg"

                when (mode) {
                    "insert" -> {
                        hm["mode"] = "insert"
                        hm["nim"] = b.edNim.text.toString()
                        hm["nama"] = b.edNamaMhs.text.toString()
                        hm["image"] = imStr
                        hm["file"] = nmFile
                        hm["nama_prodi"] = pilihProdi
                    }
                    "update" -> {
                        hm["mode"] = "update"
                        hm["nim"] = b.edNim.text.toString()
                        hm["nama"] = b.edNamaMhs.text.toString()
                        hm["image"] = imStr
                        hm["file"] = nmFile
                        hm["nama_prodi"] = pilihProdi
                    }
                    "delete" -> {
                        hm["mode"] = "delete"
                        hm["nim"] = b.edNim.text.toString()
                    }

                }
                return hm
                showDataMhs("");
            }
        }
        showDataMhs("");
        val queue = Volley.newRequestQueue(this)
        queue.add(request)

    }

    private fun getNamaProdi() {
        val request = StringRequest(
            Request.Method.GET, url2,
            { response ->
                daftarProdi.clear()
                val jsonArray = JSONArray(response)
                for (x in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(x)
                    daftarProdi.add(jsonObject.getString("nama_prodi"))
                }
                prodiAdapter.notifyDataSetChanged()
            },
            { error -> }
        )
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    private fun showDataMhs(namaMhs: String) {
        val request = object : StringRequest(Method.GET, url,
            Response.Listener { response ->
                daftarMhs.clear()
                val jsonArray = JSONArray(response)
                for (x in 0 until (jsonArray.length())) {
                    val jsonObject = jsonArray.getJSONObject(x)
                    val mhs = HashMap<String, String>()
                    mhs["nim"] = jsonObject.getString("nim")
                    mhs["nama"] = jsonObject.getString("nama")
                    mhs["nama_prodi"] = jsonObject.getString("nama_prodi")
                    mhs["url"] = jsonObject.getString("url")
                    daftarMhs.add(mhs)
                }
                mhsAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this,
                    "Terjadi kesalahan koneksi ke server",
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val hm = HashMap<String, String>()
                hm["nama"] = namaMhs
                return hm
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }
}