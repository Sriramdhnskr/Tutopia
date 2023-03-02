package com.example.tutopiaapplication.presentation.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    private val mainViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater)

//        Glide.with(this).load(R.drawable.ic_splash).into(binding.splashImg);

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                view?.findNavController()?.navigate(R.id.action_splashFragment_to_swipeLoginFragment)
            }
        },3500)

      /*  mainViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                view?.let { it1 ->
                    Navigation.findNavController(it1)
                        .navigate(R.id.action_splashFragment_to_FirstFragment)
                }
            } *//*else {
                Glide.with(this).load(R.drawable.ic_splash).into(binding.splashImg);
            }*//*
        })*/
        return binding.root
    }
}