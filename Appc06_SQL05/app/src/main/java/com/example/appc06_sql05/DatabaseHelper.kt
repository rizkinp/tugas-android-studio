package com.example.appc06_sql05

// Import library SQLiteOpenHelper dan SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Nama database dan tabel
private const val DATABASE_NAME = "Mahasiswa.db"
private const val TABLE_NAME = "mahasiswa"

// Kolom tabel
private const val COL_ID = "id"
private const val COL_NAMA = "nama"
private const val COL_PRODI = "prodi"

// Query untuk membuat tabel
private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAMA TEXT, $COL_PRODI TEXT)"

// Query untuk menghapus tabel
private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

// Kelas DatabaseHelper untuk membuat dan mengelola database
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    // Fungsi untuk menambah data mahasiswa ke database
    fun tambahMahasiswa(nama: String, prodi: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_NAMA, nama)
            put(COL_PRODI, prodi)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    // Fungsi untuk mengambil data mahasiswa dari database
    fun getMahasiswa(): List<Mahasiswa> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val mahasiswa = mutableListOf<Mahasiswa>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(COL_ID))
                val nama = getString(getColumnIndex(COL_NAMA))
                val prodi = getString(getColumnIndex(COL_PRODI))
                mahasiswa.add(Mahasiswa(id, nama, prodi))
            }
        }
        return mahasiswa
    }

    // Fungsi untuk menghapus data mahasiswa dari database
    fun hapusMahasiswa(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
    }

    // Fungsi untuk memperbarui data mahasiswa di database
    fun updateMahasiswa(mahasiswa: Mahasiswa): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_NAMA, mahasiswa.nama)
            put(COL_PRODI, mahasiswa.prodi)
        }
        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(mahasiswa.id.toString()))
    }

}


