package com.example.mapdemo.activities

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.SearchView
import androidx.fragment.app.FragmentActivity
import com.example.mapdemo.R
import com.example.mapdemo.databinding.ActivitySearchWithTextMapAcivityBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import java.io.IOException


class SearchWithTextMapActivity : FragmentActivity(), OnMapReadyCallback {
    lateinit var binding: ActivitySearchWithTextMapAcivityBinding

    lateinit var placesClient: PlacesClient
    lateinit var mMap: GoogleMap
    private lateinit var searchMap: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchWithTextMapAcivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        //Search

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyCwolhQcItFwVvnI_EMB_fPc6K1eZpTQjM");
        }
        placesClient = Places.createClient(this)

        binding.searchMap
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    val location = binding.searchMap
                        .query.toString()

                    var addressList: List<Address>? = null
                    if (location != "") {
                        val geocoder = Geocoder(applicationContext)
                        try {

                            addressList = geocoder.getFromLocationName(location, 1)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        val address = addressList!![0]

                        val latLng = LatLng(address.latitude, address.longitude)
                        mMap.addMarker(MarkerOptions().position(latLng).title(location))

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                    }
                    return true

                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            })


    }


    override fun onMapReady(p0: GoogleMap) {
        mMap = p0


    }
}