package ca.gbc.comp3074.sehatzadeh_nathan.lab4

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(
    context: Context
): SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object{
        private const val DATABASE_NAME = customers.db
        private const val DATABASE_VERSION = 1
        private const val CUSTOMER_TABLE = "CUSTOMERS"
        private const val  COLUMN_ID = "ID"
        private const val  COLUMN_NAME = "NAME"
        private const val  COLUMN_AGE = "AGE"
        private const val  COLUMN_ACTIVE = "CUSTOMER_ACTIVE"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createStatement = """
          CREATE TABLE $CUSTOMER_TABLE(
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT,
            $COLUMN_AGE INTEGER,
            $COLUMN_ACTIVE INTEGER,
          )
        """.trimIndent()
        db?.execSQL(createStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //only for development
        //TODO: provide incremental updates for each version
        db?.execSQL("DROP TABLE IF EXISTS $CUSTOMER_TABLE")
        onCreat(db)
    }
}




