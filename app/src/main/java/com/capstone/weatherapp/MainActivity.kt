package com.capstone.weatherapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var cityList : ArrayList<City>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_city_list)
        val rvCities = findViewById<View>(R.id.rvCities) as RecyclerView

        cityList = ArrayList<City>(List(100) { City("List Item", 72) })

        val adapter = CityListAdapter(cityList)
        rvCities.adapter = adapter
        rvCities.layoutManager = LinearLayoutManager(this)
    }
}
