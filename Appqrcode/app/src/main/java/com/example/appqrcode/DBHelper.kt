package com.example.appqrcode

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "students.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NIM = "nim"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PRODI = "prodi"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NIM TEXT, $COLUMN_NAME TEXT, $COLUMN_PRODI TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addStudent(student: Student) {
        val values = ContentValues().apply {
            put(COLUMN_NIM, student.nim)
            put(COLUMN_NAME, student.name)
            put(COLUMN_PRODI, student.prodi)
        }
        val db = writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val nim = cursor.getString(cursor.getColumnIndex(COLUMN_NIM))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val prodi = cursor.getString(cursor.getColumnIndex(COLUMN_PRODI))
                students.add(Student(nim, name, prodi))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return students
    }
}
