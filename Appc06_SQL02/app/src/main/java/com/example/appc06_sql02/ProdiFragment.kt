package com.example.appc06_sql02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProdiFragment : Fragment() {

    private var _binding: FragmentProdiBinding? = null
    private val binding get() = _binding!!
    private lateinit var prodiAdapter: ProdiAdapter
    private lateinit var prodiViewModel: ProdiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProdiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prodiAdapter = ProdiAdapter(::onProdiItemClick, ::onProdiItemLongClick)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = prodiAdapter
        }

        prodiViewModel = ViewModelProvider(this).get(ProdiViewModel::class.java)
        prodiViewModel.prodiList.observe(viewLifecycleOwner) { prodiList ->
            prodiAdapter.setData(prodiList)
        }

        binding.fabAddProdi.setOnClickListener {
            val action = ProdiFragmentDirections.actionProdiToAddEditProdiFragment()
            findNavController().navigate(action)
        }
    }

    private fun onProdiItemClick(prodi: Prodi) {
        val action = ProdiFragmentDirections.actionProdiToDetailProdiFragment(prodi.id)
        findNavController().navigate(action)
    }

    private fun onProdiItemLongClick(prodi: Prodi) {
        showDeleteProdiDialog(prodi)
    }

    private fun showDeleteProdiDialog(prodi: Prodi) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.dialog_delete_title))
            .setMessage(getString(R.string.dialog_delete_message, prodi.nama))
            .setPositiveButton(getString(R.string.dialog_delete_confirm)) { _, _ ->
                prodiViewModel.deleteProdi(prodi)
            }
            .setNegativeButton(getString(R.string.dialog_delete_cancel), null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

