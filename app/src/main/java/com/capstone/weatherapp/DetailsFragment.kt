package com.capstone.weatherapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.capstone.weatherapp.databinding.FragmentDetailsBinding
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

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
    private val viewModel: DetailsViewModel by activityViewModels {
        DetailsViewModelFactory((activity?.application as WeatherCacheApplication).repo)
    }
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.city.observe(viewLifecycleOwner) {
            when(it) {
                is SingleCityState.Success -> showSuccessState(it.result)
                is SingleCityState.Loading -> showLoadingState()
                is SingleCityState.Error -> showErrorState()
            }
        }
        viewModel.getSingleCityFromRepo(cityId)
    }

    fun showLoadingState() {
        binding.detailsLayout.visibility = View.GONE
        binding.stateLayout.visibility = View.VISIBLE
        binding.detailsStateIcon.visibility = View.GONE
        binding.detailsLoadingIcon.visibility = View.VISIBLE
        binding.detailsState.text = constants.EMPTY_STRING
    }

    fun showErrorState() {
        binding.detailsLayout.visibility = View.GONE
        binding.stateLayout.visibility = View.VISIBLE
        binding.detailsStateIcon.visibility = View.VISIBLE
        binding.detailsStateIcon.setImageResource(R.drawable.snag_error)
        binding.detailsLoadingIcon.visibility = View.GONE
        binding.detailsState.text = constants.ERROR_STRING
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showSuccessState(city: SingleCityResponse) {
        binding.detailsLayout.visibility = View.VISIBLE
        binding.stateLayout.visibility = View.GONE
        updateBinding(binding, city)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateBinding(binding: FragmentDetailsBinding, city: SingleCityResponse) {
        Glide.with(this)
            .load("https://openweathermap.org/img/wn/" + city!!.weather[0].icon + "@2x.png")
            .centerCrop()
            .into(binding.detailsIcon)
        binding.detailsCityName.text = city?.name
        binding.detailsCountry.text = city?.sys?.country
        binding.detailsDescription.text = city!!.weather[0].description.replaceFirstChar{it.uppercase()}
        binding.detailsTemp.text = "${city?.main?.temp?.roundToInt()}℉"
        binding.detailsLowHigh.text = "${city?.main?.temp_min?.roundToInt()}℉/${city?.main?.temp_max?.roundToInt()}℉"
        binding.detailsHumidity.text = "${city?.main?.humidity?.roundToInt()}%"
        binding.detailsWindspeed.text = "${city?.wind?.speed?.roundToInt()} MPH"
        binding.detailsPressure.text = "${city?.main?.pressure?.roundToInt()} hPa"
        binding.detailsSunrise.text = convertTime(city?.sys?.sunrise, city?.timezone)
        binding.detailsSunset.text = convertTime(city?.sys?.sunset, city?.timezone)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTime(timestamp: Int?, timeZoneOffset: Int?): String {
        val offset = ZoneOffset.ofTotalSeconds(timeZoneOffset!!)
        val instant = Instant.ofEpochSecond(timestamp!!.toLong())
        val formatter = DateTimeFormatter.ofPattern("K:mm a", Locale.ENGLISH)
        return instant.atOffset(offset).format(formatter)
    }
}