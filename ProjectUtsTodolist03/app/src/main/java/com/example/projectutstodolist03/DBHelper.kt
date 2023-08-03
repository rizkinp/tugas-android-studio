package com.example.projectutstodolist03

import android.app.DownloadManager.COLUMN_STATUS
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "todoList.db"
        private const val TABLE_NAME = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"


        private const val TABLE_TASKS = "tasks"
        private const val TABLE_HISTORY = "history"
        private const val KEY_ID = "id"
        private const val KEY_TOPIC = "topic"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE_TIME = "date_time"

        private const val INSERT_DEFAULT_USER = "INSERT INTO $TABLE_NAME ($COLUMN_USERNAME, $COLUMN_PASSWORD) VALUES " +
                "('admin', 'admin123')"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableUser = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)"
        // Create tasks table
        val CREATE_TASKS_TABLE = ("CREATE TABLE $TABLE_TASKS("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_TOPIC TEXT,"
                + "$KEY_DESCRIPTION TEXT,"
                + "$KEY_DATE_TIME TEXT"
                + ")")
        if (db != null) {
            db.execSQL(CREATE_TASKS_TABLE)
        }

        val CREATE_HISTORY_TABLE = ("CREATE TABLE $TABLE_HISTORY("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_TOPIC TEXT,"
                + "$KEY_DESCRIPTION TEXT,"
                + "$KEY_DATE_TIME TEXT,"
                + "completed_date_time TEXT"
                + ")")
        if (db != null) {
            db.execSQL(CREATE_HISTORY_TABLE)
        }

        db?.execSQL(createTableUser)
        db?.execSQL(INSERT_DEFAULT_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableUser = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableUser)
        val dropTableTask = "DROP TABLE IF EXISTS $TABLE_TASKS"
        db?.execSQL(dropTableTask)
        val dropTableHis = "DROP TABLE IF EXISTS $TABLE_HISTORY"
        db?.execSQL(dropTableHis)
        onCreate(db)
    }

    fun addUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, password)
        val result = db.insert(TABLE_NAME, null, values)
        return result != -1L
    }

    fun getUser(username: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            return User(id, username, password)
        }
        return null
    }

//    fun addTask(task: Task): Long {
//        val db = writableDatabase
//        val contentValues = ContentValues().apply {
//            put(COLUMN_TOPIC, task.topic)
//            put(COLUMN_DESCRIPTION, task.description)
//            put(COLUMN_DATE, task.date)
//            put(COLUMN_TIME, task.time)
//        }
//        return db.insert(TABLE_NAME, null, contentValues)
//    }
//    fun addTask(task: Task): Long {
//        val db = this.writableDatabase
//        val values = ContentValues().apply {
//            put(COLUMN_TOPIC, task.topic)
//            put(COLUMN_DESCRIPTION, task.description)
//            put(COLUMN_DATETIME, task.dateTime)
//            put(COLUMN_STATUS, if (task.completed) 1 else 0)
//        }
//        val id = db.insert(TABLE_NAME_TASK, null, values)
//        db.close()
//        return id
//    }



//    fun addTask(topic: Task, description: String, datetime: Date): Boolean {
//        val values = ContentValues().apply {
//            put(COLUMN_TOPIC, topic)
//            put(COLUMN_DESCRIPTION, description)
//            put(COLUMN_DATETIME, datetime.time)
//            put(COLUMN_STATUS, STATUS_PENDING)
//        }
//        val db = writableDatabase
//        val result = db.insert(TABLE_NAME, null, values)
//        db.close()
//        return result != -1L
//    }

//    fun getTasks(): List<Task> {
//        val tasks = mutableListOf<Task>()
//        val db = readableDatabase
//        val cursor = db.query(
//            TABLE_NAME,
//            arrayOf(COLUMN_ID, COLUMN_TOPIC, COLUMN_DESCRIPTION, COLUMN_DATETIME, COLUMN_STATUS),
//            null, null, null, null, "$COLUMN_DATETIME ASC"
//        )
//        if (cursor.moveToFirst()) {
//            do {
//                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
//                val topic = cursor.getString(cursor.getColumnIndex(COLUMN_TOPIC))
//                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
//                val datetime = Date(cursor.getLong(cursor.getColumnIndex(COLUMN_DATETIME)))
//                val status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS))
//                tasks.add(Task(id, topic, description, datetime, status))
//            } while (cursor.moveToNext())
//        }
//        cursor.close()
//        db.close()
//        return tasks
//    }
//
//    fun completeTask(taskId: Task): Boolean {
//        val values = ContentValues().apply {
//            put(COLUMN_STATUS, STATUS_COMPLETED)
//        }
//        val db = writableDatabase
//        val result = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
//        db.close()
//        return result > 0
//    }
//
//    fun deleteTask(taskId: Task): Boolean {
//        val db = writableDatabase
//        val result = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
//        db.close()
//        return result > 0
//    }


//    task fungsi
// Adding new task
fun addTask(task: Task) {
    val db = this.writableDatabase

    val values = ContentValues()
    values.put(KEY_TOPIC, task.topic)
    values.put(KEY_DESCRIPTION, task.description)
    values.put(KEY_DATE_TIME, task.dateTime)

    // Inserting Row
    db.insert(TABLE_TASKS, null, values)
    db.close() // Closing database connection
}

    // Getting single task
    fun getTask(id: Int): Task {
        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_TASKS, arrayOf(KEY_ID, KEY_TOPIC, KEY_DESCRIPTION, KEY_DATE_TIME),
            "$KEY_ID=?", arrayOf(id.toString()), null, null, null, null
        )

        cursor?.moveToFirst()

        val task = Task(
            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
            cursor.getString(cursor.getColumnIndex(KEY_TOPIC)),
            cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
            cursor.getString(cursor.getColumnIndex(KEY_DATE_TIME))
        )

        cursor.close()
        db.close()

        return task
    }

    // Getting All Tasks
    fun getAllTasks(): List<Task> {
        val taskList = mutableListOf<Task>()

        // Select All Query
        val selectQuery = "SELECT * FROM $TABLE_TASKS"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_TOPIC)),
                    cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE_TIME))
                )

                taskList.add(task)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        // return task list
        return taskList
    }

    fun updateTask(task: Task): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TOPIC, task.topic)
        values.put(KEY_DESCRIPTION, task.description)
        values.put(KEY_DATE_TIME, task.dateTime)

        val result = db.update(TABLE_TASKS, values, "$KEY_ID=?", arrayOf(task.id.toString()))
        db.close()

        return result != -1
    }

}
