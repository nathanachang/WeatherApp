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

class CityListFragment : Fragment() {

    private lateinit var viewModel: CityListViewModel
    private lateinit var rvCities: RecyclerView
    private var _binding: FragmentCityListBinding? = null
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
        viewModel = CityListViewModel()
        rvCities.adapter = CityListAdapter(viewModel.list)
        rvCities.layoutManager = LinearLayoutManager(context)
    }
}