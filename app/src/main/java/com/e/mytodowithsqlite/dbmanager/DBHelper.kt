package com.e.mytodowithsqlite.dbmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.e.mytodowithsqlite.model.UserModel

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
// Gets the data repository in write mode
        val db = writableDatabase
// Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(USER_NAME, user.name)
        values.put(USER_EMAIL, user.email)
        values.put(USER_PASS, user.password)

// Insert the new row, returning the primary key value of the new row
        db.insert(TABLE_NAME, null, values)
        db.close()
        return true
    }

    fun deleteUser(userid: String): Boolean{
        val qry = "Delete From $TABLE_NAME where $USER_ID = $userid"
        val db: SQLiteDatabase = this.writableDatabase
        var result: Boolean = false
        try {
            //val cursor:Int = db.delete(CUSTOMERS_TABEL_NAME, "$COLUMN_CUSTOMERID= ?", arrayOf(customerID.toString()))
            val cursor = db.execSQL(qry)
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Error Deleting")
        }
        db.close()
        return result
    }

   /* @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {
// Gets the data repository in write mode
        val db = writableDatabase
// Define 'where' part of query.
        val selection = USER_ID + " LIKE ?"
// Specify arguments in placeholder order.
        val selectionArgs = arrayOf(userid)
// Issue SQL statement.
        db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()
        return true
    }*/

    fun updateUser(id: String, userName: String, userEmail: String, userPassword: String): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        var result: Boolean
        contentValues.put(USER_NAME, userName)
        contentValues.put(USER_EMAIL, userEmail)
        contentValues.put(USER_PASS, userPassword)
        try {
            db.update(TABLE_NAME, contentValues, "$USER_ID = ?", arrayOf(id))
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Error Updating")
            result = false
        }
        return result
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    fun checkUser(email: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(USER_ID)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$USER_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'abc@d.com';
         */
        val cursor = db.query(
            TABLE_NAME, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null
        )  //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }
        return false
    }

    fun getUserIdByEmail(email: String): Int {

        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME, null, "$USER_EMAIL = ?", arrayOf(email),
            null, null, null
        )
        if (cursor.getCount() < 1) {
            return 0;
        } else {
            cursor.moveToFirst()
            val userID: Int = cursor.getString(cursor.getColumnIndex(USER_ID)).toInt()
            return userID;
        }
    }

    fun getAllUser(): ArrayList<UserModel> {

        // array of columns to fetch
        val columns = arrayOf(USER_NAME, USER_EMAIL, USER_PASS)

        // sorting orders
        val sortOrder = "$USER_NAME ASC"
        val userList = ArrayList<UserModel>()

        val db = this.readableDatabase

        // query the user table
        val cursor = db.query(
            TABLE_NAME, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder
        )         //The sort order
        if (cursor.moveToFirst()) {
            do {
                val user = UserModel(
                    name = cursor.getString(cursor.getColumnIndex(USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(USER_PASS))
                )

                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "User.db"
        val TABLE_NAME = "USER";
        val USER_ID = "USER_ID";
        val USER_NAME = "NAME";
        val USER_EMAIL = "EMAIL";
        val USER_PASS = "PASS";

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USER_NAME + " TEXT NOT NULL," +
                    USER_EMAIL + " TEXT NOT NULL," +
                    USER_PASS + " TEXT NOT NULL)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME
    }
}