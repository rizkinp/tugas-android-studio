package com.example.appc06_sql

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ProgramStudiFragment : Fragment() {

    private lateinit var viewModel: ProgramStudiViewModel
    private lateinit var adapter: ProgramStudiAdapter
    private lateinit var dialogAdd: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_program_studi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProgramStudiAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(ProgramStudiViewModel::class.java)
        viewModel.programStudi.observe(viewLifecycleOwner, { data ->
            adapter = ProgramStudiAdapter(data)
            recyclerView.adapter = adapter
        })

        dialogAdd = Dialog(requireContext())
        dialogAdd.setContentView(R.layout.dialog_add_program_studi)

        fab_add.setOnClickListener {
            dialogAdd.show()
        }

        dialogAdd.findViewById
        dialogAdd.findViewById<View>(R.id.btn_simpan).setOnClickListener {
            val id = dialogAdd.findViewById<EditText>(R.id.et_id).text.toString()
            val nama = dialogAdd.findViewById<EditText>(R.id.et_nama).text.toString()

            viewModel.addProgramStudi(ProgramStudi(id, nama))
            dialogAdd.dismiss()
        }
    }

}



