package com.example.appc06_sql_login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Menambahkan fragment Home ke container
        val homeFragment = HomeFragment()
        fragmentTransaction.add(R.id.container, homeFragment)

        // Menambahkan fragment Prodi ke container
        val prodiFragment = ProdiFragment()
        fragmentTransaction.add(R.id.container, prodiFragment)

        // Menampilkan fragment Home dan menyembunyikan fragment Prodi
        fragmentTransaction.show(homeFragment)
        fragmentTransaction.hide(prodiFragment)

        fragmentTransaction.commit()
    }
}
