package com.example.projekuts_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val bundle = intent.extras
//        if (bundle != null) {
//            val username = bundle.getString("username")
//            val fragment = HomeFragment()
//            val args = Bundle()
//            args.putString("username", username)
//            fragment.arguments = args
//        }
        // Dalam MainActivity
//        val username = intent.getStringExtra("USERNAME_KEY")
//
//        val bundle = Bundle().apply {
//            putString("USERNAME_KEY", username)
//        }
//
//        val akunFragment = AkunFragment().apply {
//            arguments = bundle
//        }
//
//        fun loadFragment(fragment: Fragment) {
//            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, akunFragment)
//                .commit()
//        }
//
//        val nameText = findViewById<TextView>(R.id.greetingTextView)
//        val username = intent.getStringExtra("name")
//        nameText.text = "Hello, $username!"
//
////        val username = intent.getStringExtra("USERNAME")
//
//        // buat objek Bundle dan masukkan nilai username
//        val bundle = Bundle()
//        bundle.putString("username", username)
//
//        // buat objek HomeFragment dan set objek Bundle
//        val homeFragment = HomeFragment()
//        homeFragment.arguments = bundle

// Dalam MainActivity
        val username = intent.getStringExtra("USERNAME_KEY")

        val bundle = Bundle().apply {
            putString("USERNAME_KEY", username)
        }

        val akunFragment = HomeFragment().apply {
            arguments = bundle
        }
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, akunFragment)
            .commit()
        navigationView = findViewById(R.id.main_navigation)
        navigationView.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemHome -> {
                loadFragment(HomeFragment())
                return true
            }
            R.id.itemRiwayat -> {
                loadFragment(HistoryFragment())
                return true
            }
            R.id.itemAkun -> {

                loadFragment(AkunFragment())
                return true
            }
        }
        return false
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }
}