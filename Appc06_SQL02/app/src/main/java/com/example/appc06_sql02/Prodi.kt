package com.example.appc06_sql02

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prodi")
data class Prodi(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nama: String,
    val akreditasi: String,
    val jenjang: String,
    val keterangan: String
)
