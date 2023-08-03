package com.example.appqrcode


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var dataInput: EditText
    private lateinit var qrImage: ImageView
    private lateinit var qrResultText: TextView
    private lateinit var dataList: ListView
    private lateinit var generateQrButton: Button
    private lateinit var scanQrButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize view components
        dataInput = findViewById(R.id.data_input)
        qrImage = findViewById(R.id.qr_image)
        qrResultText = findViewById(R.id.qr_result_text)
        dataList = findViewById(R.id.data_list)
        generateQrButton = findViewById(R.id.generate_qr_button)
        scanQrButton = findViewById(R.id.scan_qr_button)

        // Set click listener for generate QR code button
        generateQrButton.setOnClickListener {
            val data = dataInput.text.toString()
            val bitmap = generateQRCode(data)
            qrImage.setImageBitmap(bitmap)
        }

        // Set click listener for scan QR code button
        scanQrButton.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }
//        scanQrButton.setOnClickListener {
//            // Membuat intent untuk membuka galeri
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//
//            // Memulai activity untuk memilih gambar dari galeri
//            startActivityForResult(intent, PICK_IMAGE_REQUEST)
//        }

    }

    // Method to generate QR Code from data
    private fun generateQRCode(data: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val bitMatrix = MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200)
            bitmap = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)
            for (x in 0 until bitMatrix.width) {
                for (y in 0 until bitMatrix.height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) ContextCompat.getColor(this, R.color.black) else ContextCompat.getColor(this, R.color.white))
                }
            }
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }

    // Method to handle scan QR Code result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                qrResultText.text = "Hasil scan QR Code kosong"
            } else {
                qrResultText.text = result.contents
                val dataArray = result.contents.split(";")
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataArray)
                dataList.adapter = adapter
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
