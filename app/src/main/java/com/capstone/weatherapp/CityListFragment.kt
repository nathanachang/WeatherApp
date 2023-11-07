package com.capstone.weatherapp

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.weatherapp.databinding.FragmentCityListBinding
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide

class CityListFragment : Fragment() {

    private val viewModel: CityListViewModel by activityViewModels {
        CityListViewModelFactory((activity?.application as WeatherCacheApplication).repo)
    }
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCities = binding.rvCities
        cityListAdapter = CityListAdapter()
        rvCities.adapter = cityListAdapter
        rvCities.layoutManager = LinearLayoutManager(context)

        viewModel.cityList.observe(viewLifecycleOwner) {
            when(it) {
                is State.Success -> showSuccessState(it.result)
                is State.Loading -> showLoadingState()
                is State.Error -> showErrorState(it.message)
            }
        }
        viewModel.getCityListFromRepo()
    }

    fun showLoadingState() {
        binding.rvCities.visibility = View.GONE
        binding.stateLayout.visibility = View.VISIBLE
        binding.detailsState.text = ""
        Glide.with(this)
            .load(R.drawable.loading)
            .centerCrop()
            .into(binding.detailsStateIcon)
    }

    fun showErrorState(message: String) {
        binding.rvCities.visibility = View.GONE
        binding.stateLayout.visibility = View.VISIBLE
        binding.detailsState.text = message
        Glide.with(this)
            .load(R.drawable.snag_error)
            .centerCrop()
            .into(binding.detailsStateIcon)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showSuccessState(cityList: List<City>) {
        binding.rvCities.visibility = View.VISIBLE
        binding.stateLayout.visibility = View.GONE
        cityListAdapter.updateData(cityList)
    }

}