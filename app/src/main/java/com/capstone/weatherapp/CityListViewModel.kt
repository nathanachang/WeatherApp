package com.capstone.weatherapp

import androidx.lifecycle.ViewModel

class CityListViewModel : ViewModel() {
    private var _list = ArrayList<Int>(List(100) { it + 1 })
    val list: ArrayList<Int>
        get() = _list
}