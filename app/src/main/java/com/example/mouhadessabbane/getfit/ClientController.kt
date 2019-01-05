package com.example.mouhadessabbane.getfit

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class ClientController {

    lateinit var helper: DbHelper
    lateinit var db:SQLiteDatabase

    val dbtable="Client"
    val id="ID"
    val nameClient="NameClient"
    val urlPic="UrlPic"
    val poidClient="Poids"
    val tailleClient="Tailles"
    val niveauSportif="NiveauSportif"

    val dbname="gestionClient"

    constructor(context: Context){
        helper= DbHelper(context,dbname,null,1)
        db=helper.writableDatabase
    }

    fun inseretContent(name:String,url:String,poid:Float,taille:Float):Double{
        var values=ContentValues()
        values.put(nameClient,name)
        values.put(urlPic,url)
        values.put(poidClient,poid)
        values.put(tailleClient,taille)
        return db.insert(dbtable,null,values).toDouble()
    }

}