package com.capstone.weatherapp

import androidx.lifecycle.ViewModel

class CityListViewModel : ViewModel() {
    var arrayList = ArrayList<Int>(List(100) { it + 1 })
}