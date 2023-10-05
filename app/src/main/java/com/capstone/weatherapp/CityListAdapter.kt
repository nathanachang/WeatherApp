package com.capstone.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityListAdapter (private val cityList : List<Int>) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.city_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val cityView = inflater.inflate(R.layout.item_city, parent, false)

        return ViewHolder(cityView)
    }

    override fun onBindViewHolder(holder: CityListAdapter.ViewHolder, position: Int) {
        val city: Int = cityList[position]

        holder.nameTextView.text = "List Item ${city}"
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}