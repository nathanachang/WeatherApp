package com.capstone.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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
        holder.binding.temp.text = String.format("%.0f℉", cityList[position].main.temp)
        holder.binding.lowHigh.text = String.format("%.0f℉/%.0f℉", cityList[position].main.temp_min, cityList[position].main.temp_max)
        holder.binding.humidity.text = String.format("%.0f%%", cityList[position].main.humidity)

        holder.binding.cityCard.setOnClickListener {
            val onNavigateToDetails = CityListFragmentDirections.actionCityListFragmentToDetailsFragment(cityList[position].id.toString())
            holder.binding.root.findNavController().navigate(onNavigateToDetails)
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}