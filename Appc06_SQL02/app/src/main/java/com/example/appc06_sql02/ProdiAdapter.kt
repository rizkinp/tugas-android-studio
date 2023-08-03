package com.example.appc06_sql02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProdiAdapter : RecyclerView.Adapter<ProdiAdapter.ProdiViewHolder>() {

    private var prodiList: MutableList<Prodi> = mutableListOf()

    class ProdiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descTextView: TextView = itemView.findViewById(R.id.descTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prodi, parent, false)
        return ProdiViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProdiViewHolder, position: Int) {
        val prodi = prodiList[position]
        holder.nameTextView.text = prodi.name
        holder.descTextView.text = prodi.description
    }

    override fun getItemCount(): Int {
        return prodiList.size
    }

    fun setProdiList(prodis: List<Prodi>) {
        prodiList.clear()
        prodiList.addAll(prodis)
        notifyDataSetChanged()
    }

    fun addProdi(prodi: Prodi) {
        prodiList.add(prodi)
        notifyItemInserted(prodiList.size - 1)
    }

    fun updateProdi(prodi: Prodi) {
        val index = prodiList.indexOfFirst { it.id == prodi.id }
        if (index != -1) {
            prodiList[index] = prodi
            notifyItemChanged(index)
        }
    }

    fun deleteProdiById(id: Int) {
        val index = prodiList.indexOfFirst { it.id == id }
        if (index != -1) {
            prodiList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun clearProdiList() {
        prodiList.clear()
        notifyDataSetChanged()
    }
}
