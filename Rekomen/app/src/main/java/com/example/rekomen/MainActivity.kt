package com.example.rekomen


import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import jp.wasabeef.blurry.Blurry

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val containerLayout = findViewById<LinearLayout>(R.id.containerLayout)
        Blurry.with(this)
            .radius(25) // Tingkat blur
            .sampling(2) // Skala sampel
            .color(Color.argb(66, 255, 255, 255)) // Warna blur
            .async()
            .onto(containerLayout)
    }
}





