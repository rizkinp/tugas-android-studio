package com.example.projetutstodolist05

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class UserFragment : Fragment() {

    private lateinit var nameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var educationStatusTextView: TextView
    private lateinit var genderTextView: TextView

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        dbHelper = DatabaseHelper(requireContext())

        nameTextView = view.findViewById(R.id.tvName)
        usernameTextView = view.findViewById(R.id.tvUsername)
        educationStatusTextView = view.findViewById(R.id.tvStatusPendidikan)
        genderTextView = view.findViewById(R.id.tvGender)



        // Check if user data exists
        val username = "rizkinp"
        val user = dbHelper.getUserByUsername(username)

        if (user != null) {
            nameTextView.text = "Name: ${user.name}"
            usernameTextView.text = "Username: ${user.username}"
            educationStatusTextView.text = "Status Pendidikan: ${user.statusPendidikan}"
            genderTextView.text = "Gender: ${user.gender}"
        }
        return view
    }
}

