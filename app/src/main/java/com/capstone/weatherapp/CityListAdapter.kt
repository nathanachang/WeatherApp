package com.capstone.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.MutableLiveData
import com.capstone.weatherapp.databinding.ItemCityBinding

class CityListAdapter (private val cityList : MutableLiveData<List<City>>) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private var cities: List<City> = cityList.value ?: emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.city_name)
        val countryTextView = itemView.findViewById<TextView>(R.id.country)
        val tempTextView = itemView.findViewById<TextView>(R.id.temp)
        val lowHighTextView = itemView.findViewById<TextView>(R.id.low_high)
        val humidityTextView = itemView.findViewById<TextView>(R.id.humidity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val cityView = inflater.inflate(R.layout.item_city, parent, false)

        return ViewHolder(cityView)
    }

    override fun onBindViewHolder(holder: CityListAdapter.ViewHolder, position: Int) {
        holder.nameTextView.text = cities[position].name
        holder.countryTextView.text = cities[position].sys.country
        holder.tempTextView.text = "${cities[position].main.temp}℉"
        holder.lowHighTextView.text = "${cities[position].main.tempMin}℉/${cities[position].main.tempMax}℉"
        holder.humidityTextView.text = "${cities[position].main.humidity}%"
    }

    override fun getItemCount(): Int {
        return cities.size
    }
}