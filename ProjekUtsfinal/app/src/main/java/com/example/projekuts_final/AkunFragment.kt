package com.example.projekuts_final

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class AkunFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var nameTv: TextView
    private lateinit var emailTv: TextView
    private lateinit var genderTv: TextView
    private lateinit var educationStatusTv: TextView
    private lateinit var buttonLogout: TextView
    private lateinit var dbHelper: DatabaseHandler
    private var myVariable: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        profileImage = view.findViewById(R.id.profile_image)
        nameTv = view.findViewById(R.id.name_tv)
        emailTv = view.findViewById(R.id.email_tv)
        genderTv = view.findViewById(R.id.gender_tv)
        educationStatusTv = view.findViewById(R.id.education_status_tv)
        buttonLogout = view.findViewById(R.id.button_logout)
        dbHelper = DatabaseHandler(requireContext())
//        val bundle: Bundle? = arguments
//        val myValue: String? = bundle?.getString("username", "")
//        val username: String? = arguments?.getString("USERNAME_KEY")

//        val username = intent.getStringExtra("name")
            val username = "rizki"

            if (username != null) {
                val user = dbHelper.getUserByUsername(username)
                if (user != null) {
                    nameTv.setText(user.name)
                    emailTv.setText(user.email)
                    genderTv.setText(user.gender)
                    educationStatusTv.setText(user.status)
                    Toast.makeText(requireContext(), "DATA TAMPIL", Toast.LENGTH_SHORT).show()
                } else {
                    // Tampilkan pesan kesalahan jika user tidak ditemukan di database
                    Toast.makeText(requireContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }

        buttonLogout.setOnClickListener(View.OnClickListener {
            // aksi untuk melakukan
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        })


        profileImage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            profileImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 100
    }
}

