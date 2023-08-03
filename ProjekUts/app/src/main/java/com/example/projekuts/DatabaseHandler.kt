package com.example.projekuts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "BankingAppDatabase"
        private const val TABLE_USERS = "Users"
        private const val TABLE_TRANSACTIONS = "Transactions"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_PASSWORD = "password"
        private const val KEY_BALANCE = "balance"
        private const val KEY_TIMESTAMP = "timestamp"
        private const val KEY_FROM_ACCOUNT = "from_account"
        private const val KEY_TO_ACCOUNT = "to_account"
        private const val CREATE_USERS_TABLE = ("CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_BALANCE + " REAL" + ")")
        private const val CREATE_TRANSACTIONS_TABLE = ("CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FROM_ACCOUNT + " INTEGER,"
                + KEY_TO_ACCOUNT + " INTEGER,"
                + KEY_BALANCE + " REAL,"
                + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(" + KEY_FROM_ACCOUNT + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_TO_ACCOUNT + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + ")" + ")")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_USERS_TABLE)
        db?.execSQL(CREATE_TRANSACTIONS_TABLE)

        // insert default user
        val values = ContentValues().apply {
            put(KEY_NAME, "Rizki")
            put(KEY_PASSWORD, "123456")
            put(KEY_BALANCE, 10000.0)
        }
        db?.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS)
        onCreate(db)
    }

    // methods to add, update, delete, and retrieve data from the database
    // method to add user to database
    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, user.name)
        values.put(KEY_PASSWORD, user.password)
        values.put(KEY_BALANCE, user.balance)
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    // method to update user in database
    fun updateUser(user: User): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, user.name)
        values.put(KEY_PASSWORD, user.password)
        values.put(KEY_BALANCE, user.balance)
        val rowsAffected = db.update(TABLE_USERS, values, "$KEY_ID=?", arrayOf(user.id.toString()))
        db.close()
        return rowsAffected
    }

    // method to delete user from database
    fun deleteUser(user: User) {
        val db = this.writableDatabase
        db.delete(TABLE_USERS, "$KEY_ID=?", arrayOf(user.id.toString()))
        db.close()
    }

    // method to get all users from database
    fun getAllUsers(): ArrayList<User> {
        val userList = ArrayList<User>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                val password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
                val balance = cursor.getDouble(cursor.getColumnIndex(KEY_BALANCE))
                val user = User(id, name, password, balance)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    // method to add transaction to database
    fun addTransaction(transaction: Transaction): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_FROM_ACCOUNT, transaction.fromAccount)
        values.put(KEY_TO_ACCOUNT, transaction.toAccount)
        values.put(KEY_BALANCE, transaction.balance)
        val id = db.insert(TABLE_TRANSACTIONS, null, values)
        db.close()
        return id
    }

    // method to get all transactions from database
    fun getAllTransactions(): ArrayList<Transaction> {
        val transactionList = ArrayList<Transaction>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TRANSACTIONS", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val fromAccount = cursor.getInt(cursor.getColumnIndex(KEY_FROM_ACCOUNT))
                val toAccount = cursor.getInt(cursor.getColumnIndex(KEY_TO_ACCOUNT))
                val balance = cursor.getDouble(cursor.getColumnIndex(KEY_BALANCE))
                val timestamp = cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP))
                val transaction = Transaction(id, fromAccount, toAccount, balance, timestamp)
                transactionList.add(transaction)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return transactionList
    }

    // method to get user by ID from database
    fun getUserById(id: Int): User? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_USERS, arrayOf(KEY_ID, KEY_NAME, KEY_PASSWORD, KEY_BALANCE), "$KEY_ID=?", arrayOf(id.toString()), null, null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
            val password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
            val balance = cursor.getDouble(cursor.getColumnIndex(KEY_BALANCE))
            User(id, name, password, balance)
        } else {
            null
        }
    }


    //get user by name
    fun getUserByName(name: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $KEY_NAME = ?"
        val cursor = db.rawQuery(query, arrayOf(name))
        var user: User? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val userName = cursor.getString(cursor.getColumnIndex(KEY_NAME))
            val password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
            val balance = cursor.getDouble(cursor.getColumnIndex(KEY_BALANCE))
            user = User(id, userName, password, balance)
        }
        cursor.close()
        db.close()
        return user
    }

}
