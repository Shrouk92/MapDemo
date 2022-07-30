package com.example.mapdemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mapdemo.R
import com.example.mapdemo.databinding.FragmentNavHostBinding




class NavHostFragment : Fragment() {
    private lateinit var binding: FragmentNavHostBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNavHostBinding.inflate(inflater,container,false)


        binding.button1.setOnClickListener(View.OnClickListener {
            navController=findNavController()
                navController.navigate(R.id.action_navHostFragment_to_mapsFragmentWithIntent)
        })
        binding.button2.setOnClickListener(View.OnClickListener {
            navController=findNavController()
            navController.navigate(R.id.action_navHostFragment_to_searchWithTextMapActivity)
        })
        binding.button3.setOnClickListener(View.OnClickListener {
            navController=findNavController()
            navController.navigate(R.id.action_navHostFragment_to_searchWithVoiceMapActivity)
        })
        binding.button4.setOnClickListener(View.OnClickListener {
            navController=findNavController()
            navController.navigate(R.id.action_navHostFragment_to_satalliteViewMapFrgment)
        })


        // Inflate the layout for this fragment
        return binding.root
    }


}