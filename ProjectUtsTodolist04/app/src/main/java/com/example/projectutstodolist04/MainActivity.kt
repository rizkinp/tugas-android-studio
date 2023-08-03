package com.example.projectutstodolist04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Inisialisasi view
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        fragmentContainer = findViewById(R.id.fragment_container)

        // Set listener untuk navigasi antar fragment
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_home -> {
                    showFragment(HomeFragment.newInstance())
                    true
                }
                R.id.menu_item_add_todo -> {
                    showFragment(AddTodoFragment.newInstance())
                    true
                }
                else -> false
            }
        }

        // Tampilkan fragment pertama kali
        showFragment(HomeFragment.newInstance())
    }

    private fun setSupportActionBar(toolbar: Toolbar?) {

    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
