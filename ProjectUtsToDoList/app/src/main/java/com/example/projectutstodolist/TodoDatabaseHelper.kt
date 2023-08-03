package com.example.projectutstodolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.wear.ongoing.Status
import java.time.ZoneId

class TodoDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "todo.db"

        // User table
        private const val TABLE_USER = "user"
        private const val COL_USER_ID = "user_id"
        private const val COL_USER_USERNAME = "username"
        private const val COL_USER_PASSWORD = "password"

        // Task table
        private const val TABLE_TASK = "task"
        private const val COL_TASK_ID = "task_id"
        private const val COL_TASK_TITLE = "title"
        private const val COL_TASK_DESCRIPTION = "description"
        private const val COL_TASK_DATE = "date"
        private const val COL_TASK_TIME = "time"
        private const val COL_TASK_STATUS = "status"
        private const val COL_TASK_USER_ID = "user_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable =
            "CREATE TABLE $TABLE_USER ($COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_USER_USERNAME TEXT, $COL_USER_PASSWORD TEXT)"

        val createTaskTable =
            "CREATE TABLE $TABLE_TASK ($COL_TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_TASK_TITLE TEXT, $COL_TASK_DESCRIPTION TEXT, $COL_TASK_DATE TEXT, " +
                    "$COL_TASK_TIME TEXT, $COL_TASK_STATUS INTEGER, $COL_TASK_USER_ID INTEGER, " +
                    "FOREIGN KEY($COL_TASK_USER_ID) REFERENCES $TABLE_USER($COL_USER_ID))"

        db?.execSQL(createUserTable)
        db?.execSQL(createTaskTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        onCreate(db)
    }

    fun insertUser(username: String, password: String): Long {
        val values = ContentValues().apply {
            put(COL_USER_USERNAME, username)
            put(COL_USER_PASSWORD, password)
        }

        val db = writableDatabase
        val id = db.insert(TABLE_USER, null, values)
        db.close()
        return id
    }

    fun getUser(username: String, password: String): User? {
        val selectQuery =
            "SELECT * FROM $TABLE_USER WHERE $COL_USER_USERNAME = '$username' " +
                    "AND $COL_USER_PASSWORD = '$password'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var user: User? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COL_USER_ID))
            val name = cursor.getString(cursor.getColumnIndex(COL_USER_USERNAME))
            val pass = cursor.getString(cursor.getColumnIndex(COL_USER_PASSWORD))
            user = User(id, name, pass)
        }

        cursor.close()
        db.close()
        return user
    }

    // Fungsi untuk menambahkan task baru
    fun addTask(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_TASK_TITLE, task.title)
        values.put(COL_TASK_STATUS, task.isComplete)
        values.put(COL_TASK_DUE_DATE, task.)
        values.put(COLUMN_TASK_DUE_TIME, task.dueTime)
        db.insert(TABLE_TASKS, null, values)
        db.close()
    }

    // Fungsi untuk mengambil semua task
    fun getAllTasks(): ArrayList<Task> {
        val tasks = ArrayList<Task>()
        val selectQuery = "SELECT  * FROM $TABLE_TASKS ORDER BY $COLUMN_TASK_DUE_DATE ASC, $COLUMN_TASK_DUE_TIME ASC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_STATUS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DUE_DATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DUE_TIME))
                )
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return tasks
    }

    // Fungsi untuk mengambil task berdasarkan ID
    fun getTaskById(taskId: Int): Task? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_TASKS,
            arrayOf(COLUMN_TASK_ID, COLUMN_TASK_NAME, COLUMN_TASK_STATUS, COLUMN_TASK_DUE_DATE, COLUMN_TASK_DUE_TIME),
            "$COLUMN_TASK_ID=?",
            arrayOf(taskId.toString()),
            null,
            null,
            null,
            null
        )
        var task: Task? = null
        if (cursor.moveToFirst()) {
            task = Task(
                cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_STATUS)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DUE_DATE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DUE_TIME))
            )
        }
        cursor.close()
        db.close()
        return task
    }

    // Fungsi untuk menghapus task berdasarkan ID
    fun deleteTaskById(taskId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_TASKS, "$COLUMN_TASK_ID = ?", arrayOf(taskId.toString()))
        db.close()
    }

    // Fungsi untuk mengupdate status task
    fun updateTaskStatus(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TASK_STATUS, task.status)
        db.update(TABLE_TASKS, values, "$COLUMN_TASK_ID = ?", arrayOf(task.id.toString()))
        db.close()
    }



}
