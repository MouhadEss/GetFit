package com.example.mouhadessabbane.getfit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class ObeseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_obese)

        val imc=intent.getStringExtra("imc")
        Toast.makeText(this,"Votre IMC est $imc", Toast.LENGTH_SHORT).show()

    }

    fun onclickO(view: View){
        var i= Intent(this,ResultActivity::class.java)
        startActivity(i)
    }
}
