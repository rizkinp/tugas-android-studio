package com.example.projekuts_final

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment() {

    private lateinit var historyListView: ListView
    private lateinit var historyList: MutableList<History>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_history, container, false)
        historyListView = rootView.findViewById(R.id.lv_history)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inisialisasi list dan adapter
        historyList = mutableListOf()
        val historyAdapter = HistoryAdapter(requireContext(), historyList)

        // Set adapter ke list view
        historyListView.adapter = historyAdapter

        // Ambil data riwayat dari database
        val dbHelper = DatabaseHandler(requireContext())
        val historyData = dbHelper.getAllHistory()

        // Tambahkan data ke dalam list dan notify adapter
        historyList.addAll(historyData)
        historyAdapter.notifyDataSetChanged()
    }
}