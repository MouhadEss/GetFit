package com.example.mouhadessabbane.getfit.data

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mouhadessabbane.getfit.R
import com.example.mouhadessabbane.getfit.model.Place
import kotlinx.android.synthetic.main.card_layout.view.*

class PlaceListAdapter(private val list:ArrayList<Place>, private val context: Context):RecyclerView.Adapter<PlaceListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItem(place:Place){
            var name: TextView =itemView.findViewById(R.id.country_ID) as TextView
            var nameDr: TextView =itemView.findViewById(R.id.city_ID) as TextView
            var imageUrl:ImageView=itemView.findViewById(R.id.imageView) as ImageView
            name.text=place.name
            nameDr.text=place.nameDr
            imageUrl.setImageResource(place.url!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.card_layout , parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: PlaceListAdapter.ViewHolder, p1: Int) {
        p0.bindItem(list[p1])
    }

}