package com.example.mapdemo.activities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mapdemo.R
import com.example.mapdemo.databinding.ActivitySearchWithVoiceMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class SearchWithVoiceMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private val RQ_SPEACH_REC = 102
    private lateinit var binding: ActivitySearchWithVoiceMapBinding
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchWithVoiceMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mymap) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        // start Voice
        binding.mic.setOnClickListener(View.OnClickListener {
            startVoiceRecognition()

        })


    }


    fun startVoiceRecognition() {
        try {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("Start "))
            startActivityForResult(intent, RQ_SPEACH_REC)
        }catch (e:ActivityNotFoundException)
        {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://market.android.com/details?id=com.example.mapdemo")
            )
            startActivity(browserIntent)
        }





    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== RQ_SPEACH_REC && resultCode== Activity.RESULT_OK)
        {
            val result=data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val l=result?.get(0).toString()
            getLocation(l)
        }
    }

    private fun getLocation(location: String?) {
        var addressList: List<Address>? = null
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

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
    }
}