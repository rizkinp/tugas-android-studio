package com.example.appc06_sql05

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// Adapter untuk RecyclerView daftar mahasiswa
class MahasiswaAdapter(private val listener: MahasiswaAdapterListener) :
    RecyclerView.Adapter<MahasiswaViewHolder>() {

    private var mahasiswaList = emptyList<Mahasiswa>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {
        val binding =
            ItemMahasiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MahasiswaViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val currentMahasiswa = mahasiswaList[position]
        holder.bind(currentMahasiswa)
    }

    override fun getItemCount() = mahasiswaList.size

    fun setData(mahasiswa: List<Mahasiswa>) {
        this.mahasiswaList = mahasiswa
        notifyDataSetChanged()
    }
}
