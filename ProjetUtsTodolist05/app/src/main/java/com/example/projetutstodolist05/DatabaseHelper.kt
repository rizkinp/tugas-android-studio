package com.example.projetutstodolist05

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "coba_todo_3_db"
        private const val TABLE_NAME = "todo_list"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TOPIC = "topic"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"

        //table user
        private const val TABLE_NAME_USER= "user"
        private const val COL_ID_USER = "id"
        private const val COL_NAME_USER  = "name"
        private const val COL_USERNAME_USER = "username"
        private const val COL_PASSWORD_USER = "password"
        private const val COL_STATUS_PENDIDIKAN_USER = "status_pendidikan"
        private const val COL_GENDER_USER = "gender"
//        const val TABLE_NAME_USER = "users"
//        const val COLUMN_NAME_ID = "_id"
//        const val COLUMN_NAME_NAME = "name"
//        const val COLUMN_NAME_USERNAME = "username"
//        const val COLUMN_NAME_EMAIL = "email"
//        const val COLUMN_NAME_PASSWORD = "password"
//        const val COLUMN_NAME_STATUS = "status"
//        const val COLUMN_NAME_GENDER = "gender"
//        const val COLUMN_NAME_PROFILE_PICTURE = "profile_picture"


        //tambhan untuk history
        const val TABLE_NAME_HISTORY = "history_list"
        const val COLUMN_COMPLETED_DATE = "completed_date"
        const val COLUMN_STATUS = "status"
        private const val DEFAULT_USER = "INSERT INTO $TABLE_NAME ($COLUMN_ID, $COLUMN_TOPIC, " +
                "$COLUMN_DESCRIPTION, $COLUMN_DATE, $COLUMN_TIME) VALUES " +
                "('Todo 1', 'This is the description of todo 1', '2023-04-05', '12:00 PM')"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_TOPIC TEXT," +
                "$COLUMN_DESCRIPTION TEXT," +
                "$COLUMN_DATE TEXT," +
                "$COLUMN_TIME TEXT" +
                ")"
        db.execSQL(CREATE_TABLE)
//        db.execSQL(DEFAULT_USER)

         val CREATE_TABLE_HISTORY = """
        CREATE TABLE $TABLE_NAME_HISTORY (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_TOPIC TEXT,
            $COLUMN_DESCRIPTION TEXT,
            $COLUMN_DATE TEXT,
            $COLUMN_TIME TEXT,
            $COLUMN_COMPLETED_DATE TEXT,
            $COLUMN_STATUS TEXT
        )
    """
        db.execSQL(CREATE_TABLE_HISTORY)

        //table user
        val CREATE_TABLE_USER =
            "CREATE TABLE $TABLE_NAME_USER ($COL_ID_USER INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " $COL_NAME_USER TEXT" +
                    ", $COL_USERNAME_USER TEXT, $COL_PASSWORD_USER TEXT, " +
                    "$COL_STATUS_PENDIDIKAN_USER TEXT, $COL_GENDER_USER TEXT)"
        db.execSQL(CREATE_TABLE_USER)
//         val SQL_CREATE_TABLE_USER =
//            "CREATE TABLE $TABLE_NAME_USER (" +
//                    "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    "$COLUMN_NAME_NAME TEXT," +
//                    "$COLUMN_NAME_USERNAME TEXT UNIQUE," +
//                    "$COLUMN_NAME_EMAIL TEXT UNIQUE," +
//                    "$COLUMN_NAME_PASSWORD TEXT," +
//                    "$COLUMN_NAME_STATUS INTEGER DEFAULT 0," +
//                    "$COLUMN_NAME_GENDER TEXT" +
//                    ")"

//        db.execSQL(SQL_CREATE_TABLE_USER)

        //INSERT DEFAULT
        // Insert default user
//        val defaultUserValues = ContentValues().apply {
//            put(COLUMN_NAME_ID, "John Doe")
//            put(COLUMN_NAME_USERNAME, "johndoe")
//            put(COLUMN_NAME_EMAIL, "johndoe@example.com")
//            put(COLUMN_NAME_PASSWORD, "password")
//            put(COLUMN_NAME_STATUS, "Single")
//            put(COLUMN_NAME_GENDER, "Male")
//        }

//        db?.insert(TABLE_NAME_USER, null, defaultUserValues)

        // Menambahkan user default
        // Menambahkan data user default
        val defaultUserValues = ContentValues()
        defaultUserValues.put(COL_NAME_USER, "RIZKI NUR PRATAMA")
        defaultUserValues.put(COL_USERNAME_USER, "rizkinp")
        defaultUserValues.put(COL_PASSWORD_USER, "password")
        defaultUserValues.put(COL_STATUS_PENDIDIKAN_USER, "Undergraduate")
        defaultUserValues.put(COL_GENDER_USER, "Male")
        db.insert(TABLE_NAME_USER, null, defaultUserValues)
//        val defaultUser = User(
//            id = 0,
//            name = "User Default",
//            username = "defaultuser",
//            password = "defaultpass",
//            statusPendidikan = "Belum Sekolah",
//            gender = "Laki-Laki"
//        )
//        insertUser(defaultUser)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_HISTORY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_USER")
        onCreate(db)
    }

    fun getNextTodoId(): Int {
        val query = "SELECT MAX(${COLUMN_ID}) FROM ${TABLE_NAME}"
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


    fun addTodo(todo: Todo): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TOPIC, todo.topic)
            put(COLUMN_DESCRIPTION, todo.description)
            put(COLUMN_DATE, todo.date)
            put(COLUMN_TIME, todo.time)
        }
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun addTodoList(todo: Todo): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TOPIC, todo.topic)
        contentValues.put(COLUMN_DESCRIPTION, todo.description)
        contentValues.put(COLUMN_DATE, todo.date)
        contentValues.put(COLUMN_TIME, todo.time)
        val id = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return id
    }

//    fun getAllTodos(): MutableList<Todo> {
//        val todoList = mutableListOf<Todo>()
//        val selectQuery = "SELECT * FROM $TABLE_NAME"
//        val db = this.readableDatabase
//        val cursor = db.rawQuery(selectQuery, null)
//        if (cursor.moveToFirst()) {
//            do {
//                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
//                val topic = cursor.getString(cursor.getColumnIndex(COLUMN_TOPIC))
//                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
//                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
//                val time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME))
//                val todo = Todo(
//                    id = id,
//                    topic = topic,
//                    description = description,
//                    date = date,
//                    time = time
//                )
//                todoList.add(todo)
//            } while (cursor.moveToNext())
//        }
//        cursor.close()
//        db.close()
//        return todoList
//    }

    fun deleteTodoById(todoId: Int): Int {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(todoId.toString())
        return db.delete(TABLE_NAME, selection, selectionArgs)

    }


//    fun deleteTodoById(id: Int): Boolean {
//        val db = this.writableDatabase
//        val success = db.delete(TABLE_NAME, "$COLUMN_ID=$id", null)
//        db.close()
//        return (Integer.parseInt("$success") != -1)
//    }

//    fun deleteTodoById(id: Int): Int {
//        val db = writableDatabase
//        val selection = "${COLUMN_ID} = ?"
//        val selectionArgs = arrayOf(id.toString())
//        val deletedRows = db.delete(TABLE_NAME, selection, selectionArgs)
//        db.close()
//        return deletedRows
//    }


    fun getAllTodoList(): ArrayList<Todo> {
        val todoList = ArrayList<Todo>()
        val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_DATE ASC"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID ))
                val topic = cursor.getString(cursor.getColumnIndex(COLUMN_TOPIC))
                val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                val time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME))

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

    fun updateTodoList(todo: Todo) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TOPIC, todo.topic)
        values.put(COLUMN_DESCRIPTION, todo.description)
        values.put(COLUMN_DATE, todo.date)
        values.put(COLUMN_TIME, todo.time)
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(todo.id.toString()))
        db.close()
    }

//    fun getLastTodoId(): Int {
//        val db = this.readableDatabase
//        val query = "SELECT MAX(${TodoListTable.COLUMN_ID}) FROM ${TodoListTable.TABLE_NAME}"
//        val cursor = db.rawQuery(query, null)
//        var lastId = 0
//        if (cursor.moveToFirst()) {
//            lastId = cursor.getInt(0)
//        }
//        cursor.close()
//        return lastId
//    }

    fun insertDefaultTodoList(topik: String, deskripsi: String, tanggal: String, waktu: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TOPIC, topik)
            put(COLUMN_DESCRIPTION, deskripsi)
            put(COLUMN_DATE, tanggal)
            put(COLUMN_TIME, waktu)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    //METHOD UNTUK HISTORY TABLE
//    fun addHistory(history: History) {
//        val db = this.writableDatabase
//
//        val values = ContentValues().apply {
//            put(COLUMN_ID, history.id)
//            put(COLUMN_TOPIC, history.topic)
//            put(COLUMN_DESCRIPTION, history.description)
//            put(COLUMN_DATE, history.date)
//            put(COLUMN_TIME, history.time)
//            put(COLUMN_COMPLETED_DATE, history.completedDate)
//            put(COLUMN_STATUS, history.status)
//        }
//
//        db.insert(TABLE_NAME_HISTORY, null, values)
//        db.close()
//    }

//    fun insertTodoItem(todo: Todo) {
//        val values = ContentValues().apply {
//            put(DatabaseHelper.COLUMN_TOPIC, todo.todo)
//        }
//        writableDatabase.insert(TodoListContract.TodoListEntry.TABLE_NAME, null, values)
//    }

    fun getAllHistory(): List<History> {
        val historyList = mutableListOf<History>()

        val selectQuery = "SELECT * FROM $TABLE_NAME_HISTORY ORDER BY $COLUMN_COMPLETED_DATE DESC"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val history = History(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TOPIC)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TIME)),
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

    fun deleteHistoryById(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertHistory(todoItem: Todo) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TOPIC, todoItem.topic)
            put(COLUMN_DESCRIPTION, todoItem.description)
            put(COLUMN_DATE, todoItem.date)
            put(COLUMN_TIME, todoItem.time)
            put(COLUMN_COMPLETED_DATE, LocalDate.now().toString())
            put(COLUMN_STATUS, "Completed")
        }
        db.insert(TABLE_NAME_HISTORY, null, values)
        db.close()
    }

    fun deleteData(todoItem: Todo) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(todoItem.id.toString()))
        val values = ContentValues().apply {
            put(COLUMN_TOPIC, todoItem.topic)
            put(COLUMN_DESCRIPTION, todoItem.description)
            put(COLUMN_DATE, todoItem.date)
            put(COLUMN_TIME, todoItem.time)
            put(COLUMN_COMPLETED_DATE, getCurrentDate())
            put(COLUMN_STATUS, "completed")
        }
        db.insert(TABLE_NAME_HISTORY, null, values)
        db.close()
    }
    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date()
        return formatter.format(date)
    }

    fun addHistory(historyItem: History) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TOPIC, historyItem.topic)
            put(COLUMN_DESCRIPTION, historyItem.description)
            put(COLUMN_DATE, historyItem.date)
            put(COLUMN_TIME, historyItem.time)
            put(COLUMN_COMPLETED_DATE, historyItem.completedDate)
            put(COLUMN_STATUS, historyItem.status)
        }

        db.insert(TABLE_NAME_HISTORY, null, values)

        db.close()
    }


//    fun getCurrentUser(): User? {
//        val db = this.readableDatabase
//        var user: User? = null
//
//        val cursor = db.rawQuery("SELECT * FROM ${TABLE_NAME_USER} WHERE ${COLUMN_NAME_STATUS} = 1", null)
//        if (cursor.moveToFirst()) {
//            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME))
//            val username = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERNAME))
//            val email = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EMAIL))
//            val status = cursor.getString(cursor.getColumnIndex(TodoListContract.User.COLUMN_NAME_STATUS))
//            val gender = cursor.getString(cursor.getColumnIndex(TodoListContract.User.COLUMN_NAME_GENDER))
//            val photoPath = cursor.getString(cursor.getColumnIndex(TodoListContract.User.COLUMN_NAME_PHOTO_PATH))
//
//            user = User(name, username, email, status, gender, photoPath)
//        }
//
//        cursor.close()
//        db.close()
//
//        return user
//    }

//    private fun insertDefaultUser(db: SQLiteDatabase) {
//        val values = ContentValues().apply {
//            put(COLUMN_NAME_USERNAME, "admin")
//            put(COLUMN_NAME_PASSWORD, "admin")
//            put(COLUMN_NAME_NAME, "Administrator")
//            put(COLUMN_NAME_EMAIL, "admin@example.com")
//            put(COLUMN_NAME_STATUS, "Online")
//            put(COLUMN_NAME_GENDER, "Male")
//        }
//        db.insert(TABLE_NAME_USER, null, values)
//    }

//    fun getCurrentUser(): User? {
//        val db = readableDatabase
//        val projection = arrayOf(
//            COLUMN_NAME_ID,
//            COLUMN_NAME_USERNAME,
//            COLUMN_NAME_PASSWORD,
//            COLUMN_NAME_NAME,
//            COLUMN_NAME_EMAIL,
//            COLUMN_NAME_STATUS,
//            COLUMN_NAME_GENDER,
//           COLUMN_NAME_PROFILE_PICTURE
//        )
//
//        val selection = "${COLUMN_NAME_USERNAME} = ?"
//        val selectionArgs = arrayOf("admin")
//
//        val cursor = db.query(
//            TABLE_NAME_USER,
//            projection,
//            selection,
//            selectionArgs,
//            null,
//            null,
//            null
//        )
//
//        var user: User? = null
//        with(cursor) {
//            if (moveToNext()) {
//                val id = getLong(getColumnIndexOrThrow(COLUMN_NAME_ID))
//                val username =
//                    getString(getColumnIndexOrThrow(COLUMN_NAME_USERNAME))
//                val password =
//                    getString(getColumnIndexOrThrow(COLUMN_NAME_PASSWORD))
//                val fullName =
//                    getString(getColumnIndexOrThrow(COLUMN_NAME_NAME))
//                val email =
//                    getString(getColumnIndexOrThrow(COLUMN_NAME_EMAIL))
//                val status =
//                    getString(getColumnIndexOrThrow(COLUMN_NAME_STATUS))
//                val gender =
//                    getString(getColumnIndexOrThrow(COLUMN_NAME_GENDER))
//                val profilePicture =
//                    getString(getColumnIndexOrThrow(COLUMN_NAME_PROFILE_PICTURE))
//                user = User(id, username, password, fullName, email, status, gender, profilePicture)
//            }
//        }
//        cursor.close()
//        return user
//    }

//    fun updateUserProfilePicture(profilePicturePath: String) {
//        val db = writableDatabase
//        val values = ContentValues().apply {
//            put(UserContract.UserEntry.COLUMN_NAME_PROFILE_PICTURE, profilePicturePath)
//        }
//
//        val selection = "${UserContract.UserEntry.COLUMN_NAME_USERNAME} = ?"
//        val selectionArgs = arrayOf("admin")
//
//        db.update(UserContract.UserEntry.TABLE_NAME, values, selection, selectionArgs)
//    }

//    fun getCurrentUser(): User? {
//        val db = writableDatabase
//        var currentUser: User? = null
//
//        val projection = arrayOf(
//            User._ID,
//            UserListContract.User.COLUMN_NAME_USERNAME,
//            UserListContract.User.COLUMN_NAME_EMAIL,
//            UserListContract.User.COLUMN_NAME_STATUS,
//            UserListContract.User.COLUMN_NAME_GENDER,
//            UserListContract.User.COLUMN_NAME_PROFILE_PICTURE
//        )
//
//        val selection = "${UserListContract.User.COLUMN_NAME_USERNAME} = ?"
//        val selectionArgs = arrayOf(AppPreferences.username)
//
//        val cursor = db.query(
//            UserListContract.User.TABLE_NAME,
//            projection,
//            selection,
//            selectionArgs,
//            null,
//            null,
//            null
//        )
//
//        if (cursor.moveToFirst()) {
//            val id = cursor.getLong(cursor.getColumnIndex(UserListContract.User._ID))
//            val username = cursor.getString(cursor.getColumnIndex(UserListContract.User.COLUMN_NAME_USERNAME))
//            val email = cursor.getString(cursor.getColumnIndex(UserListContract.User.COLUMN_NAME_EMAIL))
//            val status = cursor.getString(cursor.getColumnIndex(UserListContract.User.COLUMN_NAME_STATUS))
//            val gender = cursor.getString(cursor.getColumnIndex(UserListContract.User.COLUMN_NAME_GENDER))
//            val profilePicture = cursor.getBlob(cursor.getColumnIndex(UserListContract.User.COLUMN_NAME_PROFILE_PICTURE))
//
//            currentUser = User(id, username, email, status, gender, profilePicture)
//        }
//
//        cursor.close()
//        return currentUser
//    }

//    fun addUser(name: String, username: String, password: String, statusPendidikan: String, gender: String): Long {
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(COL_NAME, name)
//        contentValues.put(COL_USERNAME, username)
//        contentValues.put(COL_PASSWORD, password)
//        contentValues.put(COL_STATUS_PENDIDIKAN, statusPendidikan)
//        contentValues.put(COL_GENDER, gender)
//        val result = db.insert(TABLE_NAME, null, contentValues)
//        db.close()
//        return result
//    }

//    fun getUserByUsername(username: String): User? {
//        val db = this.readableDatabase
//        val query = "SELECT * FROM $TABLE_NAME_USER WHERE $COL_USERNAME_USER = ?"
//        val cursor = db.rawQuery(query, arrayOf(username))
//        if (cursor.moveToFirst()) {
//            val id = cursor.getInt(cursor.getColumnIndex(COL_ID_USER))
//            val name = cursor.getString(cursor.getColumnIndex(COL_NAME_USER))
//            val password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD_USER))
//            val statusPendidikan = cursor.getString(cursor.getColumnIndex(COL_STATUS_PENDIDIKAN_USER))
//            val gender = cursor.getString(cursor.getColumnIndex(COL_GENDER_USER))
//            return User(id, name, username, password, statusPendidikan, gender)
//        }
//        cursor.close()
//        db.close()
//        return null
//    }

    fun getUserByUsername(username: String): User? {
        val db = this.readableDatabase
        var user: User? = null
        val selectQuery = "SELECT * FROM $TABLE_NAME_USER WHERE $COL_USERNAME_USER = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(username))
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COL_ID_USER))
            val name = cursor.getString(cursor.getColumnIndex(COL_NAME_USER))
            val password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD_USER))
            val statusPendidikan = cursor.getString(cursor.getColumnIndex(COL_STATUS_PENDIDIKAN_USER))
            val gender = cursor.getString(cursor.getColumnIndex(COL_GENDER_USER))
            user = User(id, name, username, password, statusPendidikan, gender)
        }
        cursor.close()
        db.close()
        return user
    }


    fun insertUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME_USER, user.name)
        values.put(COL_USERNAME_USER, user.username)
        values.put(COL_PASSWORD_USER, user.password)
        values.put(COL_STATUS_PENDIDIKAN_USER, user.statusPendidikan)
        values.put(COL_GENDER_USER, user.gender)
        val id = db.insert(TABLE_NAME_USER, null, values)
        db.close()
        return id
    }

}
