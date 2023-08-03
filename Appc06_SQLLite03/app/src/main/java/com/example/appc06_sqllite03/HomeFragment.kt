package com.example.appc06_sqllite03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnProdi = view.findViewById<Button>(R.id.btn_prodi)
        btnProdi.setOnClickListener {
            val prodiFragment = ProdiFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.fragment_container, prodiFragment).addToBackStack(null).commit()
        }

        return view
    }
}