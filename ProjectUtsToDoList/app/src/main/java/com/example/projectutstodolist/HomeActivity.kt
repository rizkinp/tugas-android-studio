package com.example.projectutstodolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class HomeActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var homeFragment: HomeFragment
    private lateinit var riwayatFragment: RiwayatFragment
    private lateinit var accountFragment: AccountFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fragmentManager = supportFragmentManager
        homeFragment = HomeFragment()
        riwayatFragment = RiwayatFragment()
        accountFragment = AccountFragment()

        // Set default fragment
        setFragment(homeFragment)

        // Bottom navigation click listener
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    setFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_riwayat -> {
                    setFragment(riwayatFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_account -> {
                    setFragment(accountFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.fl_container, fragment)
            commit()
        }
    }
}


