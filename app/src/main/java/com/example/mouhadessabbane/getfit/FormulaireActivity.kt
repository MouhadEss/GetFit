package com.example.mouhadessabbane.getfit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_formulaire.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_normal.view.*
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class FormulaireActivity : AppCompatActivity() {


    lateinit var url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulaire)


        name.text=intent.getStringExtra("username")
        val n=intent.getStringExtra("username")
        Log.i("URL",intent.getStringExtra("loginUser"))
        url=intent.getStringExtra("loginUser")
        loadImageFromURL(url)
        Toast.makeText(this,"connection réussite Mr/Mme:$n",Toast.LENGTH_SHORT).show()
        var sportivite= arrayOf("Niveau Sportif","Paresseux","Normal","Actif")
        var adapteurList= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sportivite)
        spinner.adapter=adapteurList


    }

    private fun loadImageFromURL(url: String) {
        Picasso.with(this).load(url).placeholder(R.drawable.usricon)
            .error(R.drawable.usricon)
            .into(profilePic,object :com.squareup.picasso.Callback{
                override fun onSuccess() {

                }

                override fun onError() {

                }
            })

    }

    fun onClick(view:View){
        var modeAli="non specifier"
        if (vegetarien.isChecked) {
            modeAli="vegetarien"
        } else if (vegetalien.isChecked){
            modeAli="vegetalien"
        }else if(JMT.isChecked){
            modeAli="Mr/Mme tout le monde"
        }
        insererBd(intent.getStringExtra("username"),intent.getStringExtra("loginUser"),poids.text.toString().toFloat(),tailles.text.toString().toFloat(),spinner.selectedItemPosition.toString(),modeAli)
     var p=poids.text
        var t=tailles.text
        var tm=t.toString().toDouble()*0.01
        var imc:Double=p.toString().toDouble()/(tm.toString().toDouble()*tm.toString().toDouble())
        Log.i("imc",imc.toString())
        if(imc<18.5){
            var i=Intent(this,UnderweightAcitivity::class.java)
            i.putExtra("imc",imc.toString())
            startActivity(i)
        }else if (imc>=18.5 && imc<24.9){
            var i=Intent(this,NormalActivity::class.java)
            i.putExtra("imc",imc.toString())
            startActivity(i)
        }else if (imc>=24.9 && imc<29.9){
            var i=Intent(this,FatActivity::class.java)
            i.putExtra("imc",imc.toString())
            startActivity(i)
        }else if (imc>=29.9 && imc<39.9){
            var i=Intent(this,ObeseActivity::class.java)
            i.putExtra("imc",imc.toString())
            startActivity(i)
        }else Toast.makeText(this,"taille ou poid incorrect",Toast.LENGTH_LONG).show()

    }

    private fun insererBd(name:String,url:String,poid:Float,taille:Float,nivS:String,modal:String) {
    if (poids.text.isEmpty() && tailles.text.isEmpty())Toast.makeText(this,"entrer le poid et la taille",Toast.LENGTH_SHORT).show()
        else{
        val controller=ClientController(this)
        val result=controller.inseretContent(name,url,poid,taille,nivS,modal)
    }

    }


}
