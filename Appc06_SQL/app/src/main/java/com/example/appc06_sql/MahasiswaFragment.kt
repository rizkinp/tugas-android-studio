package com.example.appc06_sql

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import androidx.fragment.app.Fragment

class MahasiswaFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var btnAdd: Button
    private lateinit var spinnerProgramStudi: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mahasiswa, container, false)

        listView = view.findViewById(R.id.list_mahasiswa)
        btnAdd = view.findViewById(R.id.btn_add_mahasiswa)
        spinnerProgramStudi = view.findViewById(R.id.spinner_program_studi)

        val programStudiList = listOf("Program Studi A", "Program Studi B", "Program Studi C")
        val programStudiAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, programStudiList)
        spinnerProgramStudi.adapter = programStudiAdapter

        // Implement CRUD operation for Mahasiswa here

        return view
    }
}


