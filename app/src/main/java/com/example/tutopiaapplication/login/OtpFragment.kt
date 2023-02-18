package com.example.tutopiaapplication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentLoginBinding
import com.example.tutopiaapplication.databinding.FragmentOtpBinding
import com.example.tutopiaapplication.databinding.TitleLayoutBinding

class OtpFragment : Fragment() {

    private lateinit var binding : FragmentOtpBinding

    private lateinit var titleLayoutBinding: TitleLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentOtpBinding.inflate(inflater)

        titleLayoutBinding = binding.title

        titleLayoutBinding.headerTxt.text = "Otp".uppercase()

        titleLayoutBinding.backImg.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        return binding.root
    }

}