package com.example.mouhadessabbane.getfit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_formulaire.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.widget.Toast
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


}
