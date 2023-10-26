package com.capstone.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.capstone.weatherapp.databinding.FragmentDetailsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CITYID = "cityId"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    private lateinit var cityId: String
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityId = it.getString(CITYID).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.city.observe(viewLifecycleOwner) {
            updateBinding(binding, it)
        }
        viewModel.getCity(cityId)
    }

    fun updateBinding(binding: FragmentDetailsBinding, city: SingleCityResponse?) {
        Glide.with(this)
            .load("https://openweathermap.org/img/wn/" + city!!.weather[0].icon + "@2x.png")
            .centerCrop()
            .into(binding.detailsIcon)
        binding.detailsCityName.text = city?.name
        binding.detailsCountry.text = city?.sys?.country
        binding.detailsDescription.text = city!!.weather[0].description.replaceFirstChar{it.uppercase()}
        binding.detailsTemp.text = String.format("%.0f℉", city?.main?.temp)
        binding.detailsLowHigh.text = String.format("%.0f℉/%.0f℉", city?.main?.temp_min, city?.main?.temp_max)
        binding.detailsHumidity.text = String.format("%.0f%", city?.main?.humidity)
        binding.detailsWindspeed.text = String.format("%.2f MPH", city?.wind?.speed)
        binding.detailsPressure.text = String.format("%.0f hPa", city?.main?.pressure)
    }
}