package com.example.mouhadessabbane.getfit

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    val dbtable="Client"
    val id="ID"
    val nameClient="NameClient"
    val urlPic="UrlPic"
    val poidClient="Poids"
    val tailleClient="Tailles"
    val niveauSportif="NiveauSportif"
    val modeAli="ModeAlimentaire"


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("Create Table $dbtable ($id INTEGER PRIMARY KEY AUTOINCREMENT  , $nameClient TEXT NOT NULL, $urlPic TEXT NOT NULL, $poidClient FLOAT NOT NULL, $tailleClient FLOAT NOT NULL, $niveauSportif TEXT NOT NULL, $modeAli TEXT NOT NULL);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop Table IF EXISTS $dbtable;")
    }



}