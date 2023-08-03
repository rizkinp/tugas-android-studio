package com.example.appc06_sql05

interface MahasiswaAdapterListener {
    fun onEdit(mahasiswa: Mahasiswa)
    fun onDelete(id: Int)
}