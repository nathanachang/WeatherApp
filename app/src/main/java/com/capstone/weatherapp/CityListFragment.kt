package com.capstone.weatherapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.weatherapp.databinding.FragmentCityListBinding
import androidx.fragment.app.viewModels

class CityListFragment : Fragment() {

    private val viewModel: CityListViewModel by viewModels()
    private lateinit var rvCities: RecyclerView
    private var _binding: FragmentCityListBinding? = null
    private lateinit var cityListAdapter: CityListAdapter
    val binding: FragmentCityListBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCities = binding.rvCities
        cityListAdapter = CityListAdapter()
        rvCities.adapter = cityListAdapter
        rvCities.layoutManager = LinearLayoutManager(context)

        viewModel.cityList.observe(viewLifecycleOwner) {
            cityListAdapter.updateData(it)
        }
        viewModel.getCities()
    }
}