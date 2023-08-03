package com.example.projekuts_final

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        //Nama Database
        private const val DB_NAME = "coba_todo_10_db"
        private const val DB_VERSION = 1

        //table user
        private const val TABLE_NAME_USER = "user"
        private const val COLUMN_NAME_USER = "name"
        private const val COLUMN_UNAME_USER = "username"
        private const val COLUMN_EMAIL_USER = "email"
        private const val COLUMN_PASSWORD_USER = "password"
        private const val COLUMN_GENDER_USER = "gender"
        private const val COLUMN_STATUS_USER = "status"
        private const val COLUMN_PICTURE = "picture"

        //TABLE TODOLIST
        private const val TABLE_NAME_TODO = "todo_list"
        private const val COLUMN_ID_TODO = "id"
        private const val COLUMN_TOPIC_TODO = "topic"
        private const val COLUMN_DESCRIPTION_TODO = "description"
        private const val COLUMN_DATE_TODO = "date"
        private const val COLUMN_TIME_TODO = "time"
        private const val COLUMN_USERNAME_TODO = "username"

        //TABLE HISTORY
        const val TABLE_NAME_HISTORY = "history_list"
        const val COLUMN_COMPLETED_DATE = "completed_date"
        const val COLUMN_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {

        //CREATE TABLE USER
        val CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME_USER " +
                "($COLUMN_NAME_USER TEXT, $COLUMN_UNAME_USER TEXT PRIMARY KEY, $COLUMN_EMAIL_USER TEXT, " +
                "$COLUMN_PASSWORD_USER TEXT, $COLUMN_GENDER_USER TEXT, $COLUMN_STATUS_USER TEXT)"

        //CREATE TABLE TODO
        val CREATE_TABLE_TODO = "CREATE TABLE $TABLE_NAME_TODO (" +
                "$COLUMN_ID_TODO INTEGER PRIMARY KEY," +
                "$COLUMN_TOPIC_TODO TEXT," +
                "$COLUMN_DESCRIPTION_TODO TEXT," +
                "$COLUMN_DATE_TODO TEXT," +
                "$COLUMN_TIME_TODO TEXT," +
                "$COLUMN_USERNAME_TODO TEXT"+
                ")"

        //CREATE TABLE HISTORY
        val CREATE_TABLE_HISTORY = """
        CREATE TABLE $TABLE_NAME_HISTORY (
            $COLUMN_ID_TODO INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_TOPIC_TODO TEXT,
            $COLUMN_DESCRIPTION_TODO TEXT,
            $COLUMN_DATE_TODO TEXT,
            $COLUMN_TIME_TODO TEXT,
            $COLUMN_COMPLETED_DATE TEXT,
            $COLUMN_STATUS TEXT
        )
    """

        //EXECUTE CREATE TABLE
        db.execSQL(CREATE_TABLE_TODO)
        db.execSQL(CREATE_TABLE_USER)
        db.execSQL(CREATE_TABLE_HISTORY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_TODO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_HISTORY")
        onCreate(db)
    }


    //=========GRAN FUNGSI USER+++++++++++
    //FUNGSI MENAMBAHKAN USER PADA REGISTER
    fun insertUser(user: User) {
        val values = ContentValues()
        values.put(COLUMN_NAME_USER, user.name)
        values.put(COLUMN_UNAME_USER, user.username)
        values.put(COLUMN_EMAIL_USER, user.email)
        values.put(COLUMN_PASSWORD_USER, user.password)
        values.put(COLUMN_GENDER_USER, user.gender)
        values.put(COLUMN_STATUS_USER, user.status)
        val db = this.writableDatabase
        db.insert(TABLE_NAME_USER, null, values)
        db.close()
    }

    //FUNGSI MENGAMBIL DATA PENGGUNA UNTUK LOGIN
    fun getUser(username: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_USER WHERE $COLUMN_UNAME_USER = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        if (cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndex(COLUMN_UNAME_USER))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_USER))
            return User( "", username, "", password, "", "")
        }
        return null
    }

    //FUNGSI MENGAMBIL DATA USER
    fun tampilkanDataUser(username: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_USER WHERE $COLUMN_UNAME_USER = '$username'"
        val cursor = db.rawQuery(query, null)
        var user: User? = null
        if (cursor.moveToFirst()) {
            val nama = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_USER))
            val gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER_USER))
            val statusPendidikan = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS_USER))
            user = User(nama,  username, email,"", gender, statusPendidikan)
        }
        cursor.close()
        db.close()
        return user
    }

    //FUNGSI MENGAMBIL DATA BERDDASARKAN USERNAME
    fun getUserByUsername(username: String): User? {
        val selectQuery = "SELECT * FROM $TABLE_NAME_USER WHERE $COLUMN_UNAME_USER = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(username))
        var user: User? = null

        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_USER))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_USER))
            val gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER_USER))
            val status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS_USER))

            user = User(name, username, email, password, gender, status)
        }

        cursor.close()
        db.close()

        return user
    }


    //===========GRAN FUNGSI TODO==============

    //FUNGSI MENGAMBIL ID OTOMATIS
    fun getNextTodoId(): Int {
        val query = "SELECT MAX(${COLUMN_ID_TODO}) FROM $TABLE_NAME_TODO"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        var maxId = 0
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return maxId + 1
    }

    //FUNGSI MENAMBAHKAN TODO
    fun addTodo(todo: Todo): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TOPIC_TODO, todo.topic)
            put(COLUMN_DESCRIPTION_TODO, todo.description)
            put(COLUMN_DATE_TODO, todo.date)
            put(COLUMN_TIME_TODO, todo.time)
        }
        val success = db.insert(TABLE_NAME_TODO, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    //FUNGSI MENGHAPUS TODO
    fun deleteTodoById(todoId: Int): Int {
        val db = writableDatabase
        val selection = "$COLUMN_ID_TODO = ?"
        val selectionArgs = arrayOf(todoId.toString())
        return db.delete(TABLE_NAME_TODO, selection, selectionArgs)

    }

    //FUNGSI MENAMPILKAN SELURUH DATA TODO
    fun getAllTodoList(): ArrayList<Todo> {
        val todoList = ArrayList<Todo>()

        val selectQuery = "SELECT * FROM $TABLE_NAME_TODO ORDER BY $COLUMN_DATE_TODO ASC"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_TODO ))
                val topic = cursor.getString(cursor.getColumnIndex(COLUMN_TOPIC_TODO))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_TODO))
                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TODO))
                val time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME_TODO))

                val todo = Todo(
                    id = id,
                    topic = topic,
                    description = description,
                    date = date,
                    time = time,
                    completedDate = "",
                    status = ""
                )

                todoList.add(todo)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return todoList
    }


    //GRAND FUNGSI HISTORY
    //FUNGSI MENAMBAHKAN KE TABLE HISTORY
    fun addHistory(historyItem: History) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TOPIC_TODO, historyItem.topic)
            put(COLUMN_DESCRIPTION_TODO, historyItem.description)
            put(COLUMN_DATE_TODO, historyItem.date)
            put(COLUMN_TIME_TODO, historyItem.time)
            put(COLUMN_COMPLETED_DATE, historyItem.completedDate)
            put(COLUMN_STATUS, historyItem.status)
        }

        db.insert(TABLE_NAME_HISTORY, null, values)

        db.close()
    }

    fun getAllHistory(): List<History> {
        val historyList = mutableListOf<History>()

        val selectQuery = "SELECT * FROM $TABLE_NAME_HISTORY ORDER BY $COLUMN_COMPLETED_DATE DESC"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val history = History(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID_TODO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TOPIC_TODO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_TODO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TODO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TIME_TODO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))
                )
                historyList.add(history)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return historyList
    }

    //
//    fun getTodoList(username: String): List<Todo> {
//        val db = readableDatabase
//        val todos = mutableListOf<Todo>()
//
//        val query = "SELECT * FROM $TABLE_NAME_TODO WHERE $COLUMN_UNAME_USER = ?"
//        val cursor = db.rawQuery(query, arrayOf(username))
//
//        if (cursor.moveToFirst()) {
//            do {
//                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_TODO))
//                val topic = cursor.getString(cursor.getColumnIndex(COLUMN_TOPIC_TODO))
//                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_TODO))
//                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TODO))
//                val time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME_TODO))
////                val deadline = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
////                val status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))
//                val todo = Todo(id, topic, description, date, time, "", "", username)
//                todos.add(todo)
//            } while (cursor.moveToNext())
//        }
//
//        cursor.close()
//        db.close()
//
//        return todos
//    }

    // Fungsi untuk menampilkan data dari tabel todolist berdasarkan query
//    fun getData(query: String): ArrayList<String> {
//        val result = ArrayList<String>()
//        val db = this.readableDatabase
//        val cursor: Cursor = db.rawQuery(query, null)
//        if (cursor.moveToFirst()) {
//            do {
//                val id = cursor.getString(0)
//                val topic = cursor.getString(1)
//                val description = cursor.getString(2)
//                val date = cursor.getString(3)
//                val time = cursor.getString(4)
//                val item = "ID: $id\nTopic: $topic\nDescription: $description\nDate: $date\nTime: $time"
//                result.add(item)
//            } while (cursor.moveToNext())
//        }
//        cursor.close()
//        db.close()
//        return result
//    }


}