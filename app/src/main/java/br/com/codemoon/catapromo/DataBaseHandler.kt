package br.com.codemoon.catapromo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASE_NAME = "MyApp"
var VERSAO_BANCO = 1

val TABLE_NAME = "usuario"
var COL_ID = "id"
var COL_NOME = "nome"
var COL_EMAIL = "email"
var COL_LONGITUDE = "longitude"
var COL_LATITUDE = "latitude"

class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSAO_BANCO) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOME + " VARCHAR(255)," +
                COL_EMAIL + " VARCHAR(255)," +
                COL_LONGITUDE + " VARCHAR(255)," +
                COL_LATITUDE + " VARCHAR(255))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun verificarUsuario(user: User) {

        val db = this.writableDatabase
        val query = "SELECT * FROM " + TABLE_NAME + " WHERE id = 1"
        val result = db.rawQuery(query, null)

        if (result.count != 0) {
            this.updateData(user)
        } else {
            this.insertData(user)
        }

        db.close()
    }

    fun insertData(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_NOME, user.nome)
        values.put(COL_EMAIL, user.email)
        values.put(COL_LONGITUDE, user.longitude)
        values.put(COL_LATITUDE, user.latitude)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateData(user: User) {

        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COL_NOME, user.nome)
        values.put(COL_EMAIL, user.email)
        values.put(COL_LONGITUDE, user.longitude)
        values.put(COL_LATITUDE, user.latitude)

        val selection = COL_ID + " = ?"
        val selectionArgs = arrayOf("1")

        db.update(TABLE_NAME, values, selection, selectionArgs)
        db.close()

    }

    fun getNomeUsuario(): String {
        var nome: String = ""

        val db = this.writableDatabase
        val query = "SELECT nome FROM " + TABLE_NAME + " WHERE id = 1"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                nome = result.getString(result.getColumnIndex(COL_NOME))
            } while (result.moveToNext())
        }

        result.close()
        db.close()

        return nome
    }

    fun getEmailUsuario(): String {
        var email: String = ""

        val db = this.writableDatabase
        val query = "SELECT email FROM " + TABLE_NAME + " WHERE id = 1"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                email = result.getString(result.getColumnIndex(COL_EMAIL))
            } while (result.moveToNext())
        }

        result.close()
        db.close()

        return email
    }

}