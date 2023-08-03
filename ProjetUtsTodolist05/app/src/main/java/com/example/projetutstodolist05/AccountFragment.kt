//package com.example.projetutstodolist05
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//
//class AccountFragment : Fragment() {
//
//    private lateinit var nameTextView: TextView
//    private lateinit var emailTextView: TextView
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_account, container, false)
//
//        // Initialize views
//        nameTextView = view.findViewById(R.id.name_text_view)
//        emailTextView = view.findViewById(R.id.email_text_view)
//
//        // Set user info
//        val user = getUserInfo()
//        nameTextView.text = user.name
//        emailTextView.text = user.email
//
//        return view
//    }
//
//    private fun getUserInfo(): User {
//        // Replace with actual user info retrieval logic
//        return User("John Doe", "johndoe@example.com")
//    }
//
//    data class User(val name: String, val email: String)
//
//}
