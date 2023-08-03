package com.example.projectutstodolist03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database helper
        val dbHelper = DBHelper(this)

        // Initialize fragments
        val homeFragment = HomeFragment(dbHelper)
//        val historyFragment = HistoryFragment(dbHelper)
//        val accountFragment = AccountFragment()

        // Set up bottom navigation view
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, homeFragment).commit()
                    true
                }
                R.id.nav_history -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, homeFragment).commit()
                    true
                }
                R.id.nav_account -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, homeFragment).commit()
                    true
                }
                else -> false
            }
        }

        // Set home fragment as default fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment)
            .commit()
    }
}


