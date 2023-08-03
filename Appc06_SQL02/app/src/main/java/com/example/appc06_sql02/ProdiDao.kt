package com.example.appc06_sql02

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProdiDao {

    @Insert
    suspend fun insert(prodi: Prodi)

    @Update
    suspend fun update(prodi: Prodi)

    @Delete
    suspend fun delete(prodi: Prodi)

    @Query("DELETE FROM prodi")
    suspend fun deleteAll()

    @Query("SELECT * FROM prodi")
    fun getAllProdi(): LiveData<List<Prodi>>

    @Query("SELECT * FROM prodi WHERE id = :id")
    fun getProdiById(id: Int): LiveData<Prodi>
}
