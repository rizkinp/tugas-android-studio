package com.example.projectutstodolist04



import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoListHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "todo_list.db"

        private const val TABLE_NAME = "todo_list"
        private const val COL_ID = "id"
        private const val COL_TOPIC = "topic"
        private const val COL_DESCRIPTION = "description"
        private const val COL_DATE = "date"
        private const val COL_TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TOPIC TEXT, $COL_DESCRIPTION TEXT, $COL_DATE TEXT, $COL_TIME TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addData(todo: Todo) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TOPIC, todo.topic)
        contentValues.put(COL_DESCRIPTION, todo.description)
        contentValues.put(COL_DATE, todo.date)
        contentValues.put(COL_TIME, todo.time)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun getAllData(): ArrayList<Todo> {
        val todoList = ArrayList<Todo>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor?.moveToFirst() == true) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                val topic = cursor.getString(cursor.getColumnIndex(COL_TOPIC))
                val description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndex(COL_DATE))
                val time = cursor.getString(cursor.getColumnIndex(COL_TIME))

                val todo = Todo(
                    id = id,
                    topic = topic,
                    description = description,
                    date = date,
                    time = time
                )

                todoList.add(todo)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        db.close()
        return todoList
    }

    fun deleteData(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
        db.close()
    }
}
