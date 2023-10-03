package com.capstone.weatherapp

import androidx.lifecycle.ViewModel

class CityListViewModel : ViewModel() {
    private var _list = ArrayList<City>(List(100) { City("New York", 72) })
    val list: ArrayList<City>
        get() = _list
}