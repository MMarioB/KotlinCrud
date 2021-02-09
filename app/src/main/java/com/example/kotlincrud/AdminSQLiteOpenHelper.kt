package com.example.kotlincrud

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class AdminSQLiteOpenHelper (context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table discos(codigo int primary key, nombre text, cantante text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}