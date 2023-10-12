package com.capstone.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.weatherapp.databinding.ItemCityBinding

class CityListAdapter (private var cityList : List<City> = emptyList()) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(cities: List<City>) {
        cityList = cities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val binding = ItemCityBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityListAdapter.ViewHolder, position: Int) {
        holder.binding.cityName.text = cityList[position].name
        holder.binding.country.text = cityList[position].sys.country
        holder.binding.temp.text = "${cityList[position].main.temp}℉"
        holder.binding.lowHigh.text = "${cityList[position].main.temp_min}℉/${cityList[position].main.temp_max}℉"
        holder.binding.humidity.text = "${cityList[position].main.humidity}%"
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}