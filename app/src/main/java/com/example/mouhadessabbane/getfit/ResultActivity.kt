package com.example.mouhadessabbane.getfit

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.mouhadessabbane.getfit.data.PlaceListAdapter
import com.example.mouhadessabbane.getfit.model.Place
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_formulaire.*
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity() : AppCompatActivity() {

    private var adapter: PlaceListAdapter?=null
    private var regimeList:ArrayList<Place>?=null
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var controller:ClientController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        regimeList=ArrayList<Place>()
        layoutManager= LinearLayoutManager(this)
        adapter=PlaceListAdapter(regimeList!!,this)
        controller = ClientController(this)
        myRecyclerView.layoutManager=layoutManager
        myRecyclerView.adapter=adapter

        var nameList:Array<String> = arrayOf("The Most Authentic TV Trainer","The Anxious Meditation Specialist","The Battle-Prepping Trainer","The Honest Food Blogger")
        var drNameList:Array<String> = arrayOf("JEN WIDERSTROM","DAN HARRIS","MARTIN ROONEY","ANGELA LIDDON")
        var imagesUrl:Array<Int> = arrayOf(R.drawable.pro01 ,R.drawable.pro02 ,R.drawable.pro03 ,R.drawable.pro04 )

        for (i in 0..3){
            var place=Place()
            place.name=nameList[i]
            place.nameDr=drNameList[i]
            place.url=imagesUrl[i]
            regimeList?.add(place)
        }
        adapter!!.notifyDataSetChanged()



        var cursor=controller.affichertout()
        if (cursor!=null && cursor.moveToFirst()) {
            do {
                usrnameRes.text=cursor.getString(cursor.getColumnIndex("NameClient"))
                var url=cursor.getString(cursor.getColumnIndex("UrlPic"))
                loadImageFromURL(url)
            }while (1==2)
        }




    }

    private fun loadImageFromURL(url: String) {
        Picasso.with(this).load(url).placeholder(R.drawable.usricon)
            .error(R.drawable.usricon)
            .into(profilePicRes,object :com.squareup.picasso.Callback{
                override fun onSuccess() {

                }

                override fun onError() {

                }
            })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.monC -> {Toast.makeText(this,"succes",Toast.LENGTH_SHORT).show()
                            return super.onOptionsItemSelected(item)}
            R.id.apro -> {Toast.makeText(this,"succes",Toast.LENGTH_SHORT).show()
                            return super.onOptionsItemSelected(item)}
            else ->      return super.onOptionsItemSelected(item)
        }
    }



}
