package com.example.appc06_sqllite03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class ProdiFragment : Fragment() {

    private lateinit var adapter: ProdiAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_prodi, container, false)

        adapter = ProdiAdapter(requireContext())

        val btnInsert = view.findViewById<Button>(R.id.btn_insert)
        btnInsert.setOnClickListener {
            val nama = view.findViewById<EditText>(R.id.et_nama).text.toString()
            val deskripsi = view.findViewById<EditText>(R.id.et_deskripsi).text.toString()
            adapter.insert(nama, deskripsi)
        }

        val btnUpdate = view.findViewById<Button>(R.id.btn_update)
        btnUpdate.setOnClickListener {
            val id = view.findViewById<EditText>(R.id.et_id).text.toString().toLong()
            val nama = view.findViewById<EditText>(R.id.et_nama).text.toString()
            val deskripsi =
                view.findViewById<EditText>(R.id.et_deskripsi).adapter.update(id, nama, deskripsi)
        }

        val btnDelete = view.findViewById<Button>(R.id.btn_delete)
        btnDelete.setOnClickListener {
            val id = view.findViewById<EditText>(R.id.et_id).text.toString().toLong()
            adapter.delete(id)
        }

        return view
    }
}