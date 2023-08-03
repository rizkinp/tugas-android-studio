package com.example.appc06_sql_login

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "LoginDatabase.db"
        private const val TABLE_NAME = "users"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"

        private const val TABLE_NAME_PRODI = "prodi"
        private const val COL_ID = "id"
        private const val COL_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USERS_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")")


        db.execSQL(CREATE_USERS_TABLE)
        db.execSQL("CREATE TABLE " + "prodi" + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " + " TEXT)");

        // Insert data
        val values = ContentValues()
        values.put(COLUMN_USERNAME, "user1")
        values.put(COLUMN_PASSWORD, "pass1")
        db.insert(TABLE_NAME, null, values)

        values.put(COLUMN_USERNAME, "user2")
        values.put(COLUMN_PASSWORD, "pass2")
        db.insert(TABLE_NAME, null, values)

        values.put(COLUMN_USERNAME, "user3")
        values.put(COLUMN_PASSWORD, "pass3")
        db.insert(TABLE_NAME, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun insertData(name: String?, address: String?) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        db.insert(TABLE_NAME_PRODI, null, values)
        db.close()
    }

    fun getData(): Cursor? {
        val db = readableDatabase
        val columns =
            arrayOf<String>(COL_ID, COL_NAME)
        return db.query(TABLE_NAME_PRODI, columns, null, null, null, null, null)
    }

    fun updateData(id: Int, name: String?) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        db.update(TABLE_NAME_PRODI, values, "$COL_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteData(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME_PRODI, "$COL_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun getUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?", arrayOf(username, password))
        val result = cursor.count > 0
        cursor.close()
        return result
    }

}
