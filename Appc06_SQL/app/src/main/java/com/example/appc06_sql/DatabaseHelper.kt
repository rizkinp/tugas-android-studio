package com.example.appc06_sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Kampus.db"

        private const val TABLE_PROGRAM_STUDI = "ProgramStudi"
        private const val COLUMN_PROGRAM_STUDI_ID = "id"
        private const val COLUMN_PROGRAM_STUDI_NAMA = "namaProgramStudi"

        private const val TABLE_MAHASISWA = "Mahasiswa"
        private const val COLUMN_MAHASISWA_NIM = "nim"
        private const val COLUMN_MAHASISWA_NAMA = "nama"
        private const val COLUMN_MAHASISWA_ID_PROGRAM_STUDI = "idProgramStudi"

        private const val TABLE_MATA_KULIAH = "MataKuliah"
        private const val COLUMN_MATA_KULIAH_ID = "id"
        private const val COLUMN_MATA_KULIAH_KODE = "kode"
        private const val COLUMN_MATA_KULIAH_NAMA_MATKUL = "namaMatkul"
        private const val COLUMN_MATA_KULIAH_ID_PROGRAM_STUDI = "idProgramStudi"

        private const val TABLE_NILAI = "Nilai"
        private const val COLUMN_NILAI_ID = "id"
        private const val COLUMN_NILAI_NIM = "nim"
        private const val COLUMN_NILAI_ID_MATA_KULIAH = "idMataKuliah"
        private const val COLUMN_NILAI_NILAI = "nilai"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createProgramStudiTable =
            ("CREATE TABLE $TABLE_PROGRAM_STUDI ($COLUMN_PROGRAM_STUDI_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_PROGRAM_STUDI_NAMA TEXT NOT NULL)")
        val createMahasiswaTable = ("CREATE TABLE $TABLE_MAHASISWA (" +
                "$COLUMN_MAHASISWA_NIM TEXT PRIMARY KEY," +
                "$COLUMN_MAHASISWA_NAMA TEXT NOT NULL," +
                "$COLUMN_MAHASISWA_ID_PROGRAM_STUDI INTEGER NOT NULL," +
                "FOREIGN KEY($COLUMN_MAHASISWA_ID_PROGRAM_STUDI) REFERENCES $TABLE_PROGRAM_STUDI($COLUMN_PROGRAM_STUDI_ID))")

        val createMataKuliahTable = ("CREATE TABLE $TABLE_MATA_KULIAH (" +
                "$COLUMN_MATA_KULIAH_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_MATA_KULIAH_KODE TEXT NOT NULL," +
                "$COLUMN_MATA_KULIAH_NAMA_MATKUL TEXT NOT NULL," +
                "$COLUMN_MATA_KULIAH_ID_PROGRAM_STUDI INTEGER NOT NULL," +
                "FOREIGN KEY($COLUMN_MATA_KULIAH_ID_PROGRAM_STUDI) REFERENCES $TABLE_PROGRAM_STUDI($COLUMN_PROGRAM_STUDI_ID))")

        val createNilaiTable = ("CREATE TABLE $TABLE_NILAI (" +
                "$COLUMN_NILAI_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NILAI_NIM TEXT NOT NULL," +
                "$COLUMN_NILAI_ID_MATA_KULIAH INTEGER NOT NULL," +
                "$COLUMN_NILAI_NILAI INTEGER NOT NULL," +
                "FOREIGN KEY($COLUMN_NILAI_NIM) REFERENCES $TABLE_MAHASISWA($COLUMN_MAHASISWA_NIM)," +
                "FOREIGN KEY($COLUMN_NILAI_ID_MATA_KULIAH) REFERENCES $TABLE_MATA_KULIAH($COLUMN_MATA_KULIAH_ID))")

        db?.execSQL(createProgramStudiTable)
        db?.execSQL(createMahasiswaTable)
        db?.execSQL(createMataKuliahTable)
        db?.execSQL(createNilaiTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PROGRAM_STUDI")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MAHASISWA")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MATA_KULIAH")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NILAI")
        onCreate(db)
    }

    fun insertProgramStudi(programStudi: ProgramStudi): Long {
        val values = ContentValues()
        values.put(COLUMN_PROGRAM_STUDI_NAMA, programStudi.nama)
        val db = this.writableDatabase
        val id = db.insert(TABLE_PROGRAM_STUDI, null, values)
        db.close()
        return id
    }

    fun getProgramStudiList(): ArrayList<ProgramStudi> {
        val programStudiList = ArrayList<ProgramStudi>()
        val selectQuery = "SELECT * FROM $TABLE_PROGRAM_STUDI"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val programStudi = ProgramStudi(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_PROGRAM_STUDI_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PROGRAM_STUDI_NAMA))
                )
                programStudiList.add(programStudi)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return programStudiList
    }

    fun updateProgramStudi(programStudi: ProgramStudi): Int {
        val values = ContentValues()
        values.put(COLUMN_PROGRAM_STUDI_NAMA, programStudi.namaProgramStudi)
        val db = this.writableDatabase
        val result = db.update(
            TABLE_PROGRAM_STUDI,
            values,
            "$COLUMN_PROGRAM_STUDI_ID = ?",
            arrayOf(programStudi.id.toString())
        )
        db.close()
        return result
    }

    fun deleteProgramStudi(programStudi: ProgramStudi): Int {
        val db = this.writableDatabase
        val result = db.delete(
            TABLE_PROGRAM_STUDI,
            "$COLUMN_PROGRAM_STUDI_ID = ?",
            arrayOf(programStudi.id.toString())
        )
        db.close()
        return result
    }

    fun insertMahasiswa(mahasiswa: Mahasiswa): Long {
        val values = ContentValues()
        values.put(COLUMN_MAHASISWA_NIM, mahasiswa.nim)
        values.put(COLUMN_MAHASISWA_NAMA, mahasiswa.namaMahasiswa)
        values.put(COLUMN_MAHASISWA_ID_PROGRAM_STUDI, mahasiswa.idProgramStudi)
        val db = this.writableDatabase
        val id = db.insert(TABLE_MAHASISWA, null, values)
        db.close()
        return id
    }

    fun getMahasiswaList(): ArrayList<Mahasiswa> {
        val mahasiswaList = ArrayList<Mahasiswa>()
        val selectQuery = "SELECT * FROM $TABLE_MAHASISWA"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mahasiswa = Mahasiswa(
                    cursor.getString(cursor.getColumnIndex(COLUMN_MAHASISWA_NIM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MAHASISWA_NAMA)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_MAHASISWA_ID_PROGRAM_STUDI))
                )
                mahasiswaList.add(mahasiswa)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return mahasiswaList
    }

    fun updateMahasiswa(mahasiswa: Mahasiswa): Int {
        val values = ContentValues()
        values.put(COLUMN_MAHASISWA_NAMA, mahasiswa.namaMahasiswa)
        values.put(COLUMN_MAHASISWA_ID_PROGRAM_STUDI, mahasiswa.idProgramStudi)
        val db = this.writableDatabase
        val result = db.update(
            TABLE_MAHASISWA,
            values,
            "$COLUMN_MAHASISWA_NIM = ?",
            arrayOf(mahasiswa.nim)
        )
        db.close()
        return result
    }

    fun deleteMahasiswa(mahasiswa: Mahasiswa): Int {
        val db = this.writableDatabase
        val result = db.delete(
            TABLE_MAHASISWA,
            "$COLUMN_MAHASISWA_NIM = ?",
            arrayOf(mahasiswa.nim)
        )
        db.close()
        return result
    }

    fun insertMataKuliah(mataKuliah: MataKuliah): Long {
        val values = ContentValues()
        values.put(COLUMN_MATA_KULIAH_KODE, mataKuliah.kodeMatkul)
        values.put(COLUMN_MATA_KULIAH_NAMA_MATKUL, mataKuliah.namaMatkul)
        values.put(COLUMN_MATA_KULIAH_ID_PROGRAM_STUDI, mataKuliah.idProgramStudi)
        val db = this.writableDatabase
        val id = db.insert(
            TABLE_MATA_KULIAH, null, values
        )
        db.close()
        return id
    }

    fun getMataKuliahList(): ArrayList<MataKuliah> {
        val mataKuliahList = ArrayList<MataKuliah>()
        val selectQuery = "SELECT * FROM $TABLE_MATA_KULIAH"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mataKuliah = MataKuliah(
                    cursor.getString(cursor.getColumnIndex(COLUMN_MATA_KULIAH_KODE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MATA_KULIAH_NAMA_MATKUL)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_MATA_KULIAH_ID_PROGRAM_STUDI))
                )
                mataKuliahList.add(mataKuliah)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return mataKuliahList
    }

    fun updateMataKuliah(mataKuliah: MataKuliah): Int {
        val values = ContentValues()
        values.put(COLUMN_MATA_KULIAH_KODE, mataKuliah.kodeMatkul)
        values.put(COLUMN_MATA_KULIAH_NAMA_MATKUL, mataKuliah.namaMatkul)
        values.put(COLUMN_MATA_KULIAH_ID_PROGRAM_STUDI, mataKuliah.idProgramStudi)
        val db = this.writableDatabase
        val result = db.update(
            TABLE_MATA_KULIAH,
            values,
            "$COLUMN_MATA_KULIAH_KODE = ?",
            arrayOf(mataKuliah.kodeMatkul)
        )
        db.close()
        return result
    }

    fun deleteMataKuliah(mataKuliah: MataKuliah): Int {
        val db = this.writableDatabase
        val result = db.delete(
            TABLE_MATA_KULIAH,
            "$COLUMN_MATA_KULIAH_KODE = ?",
            arrayOf(mataKuliah.kodeMatkul)
        )
        db.close()
        return result
    }

    fun insertNilai(nilai: Nilai): Long {
        val values = ContentValues()
        values.put(COLUMN_NILAI_NIM, nilai.nim)
        values.put(COLUMN_NILAI_KODE_MATKUL, nilai.kodeMatkul)
        values.put(COLUMN_NILAI_NILAI, nilai.nilai)
        val db = this.writableDatabase
        val id = db.insert(TABLE_NILAI, null, values)
        db.close()
        return id
    }

    fun getNilaiList(): ArrayList<Nilai> {
        val nilaiList = ArrayList<Nilai>()
        val selectQuery = "SELECT * FROM $TABLE_NILAI"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val nilai = Nilai(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NILAI_NIM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NILAI_KODE_MATKUL)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NILAI_NILAI))
                )
                nilaiList.add(nilai)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return nilaiList
    }

    fun updateNilai(nilai: Nilai): Int {
        val values = ContentValues()
        values.put(COLUMN_NILAI_NILAI, nilai.nilai)
        val db = this.writableDatabase
        val result = db.update(
            TABLE_NILAI,
            values,
            "$COLUMN_NILAI_NIM = ? AND $COLUMN_NILAI_KODE_MATKUL = ?",
            arrayOf(nilai.nim, nilai.kodeMatkul)
        )
        db.close()
        return result
    }

    fun deleteNilai(nilai: Nilai): Int {
        val db = this.writableDatabase
        val result = db.delete(
            TABLE_NILAI,
            "$COLUMN_NILAI_NIM = ? AND $COLUMN_NILAI_KODE_MATKUL = ?",
            arrayOf(nilai.nim, nilai.kodeMatkul)
        )
        db.close()
        return result
    }
}

