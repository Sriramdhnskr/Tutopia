package com.example.tutopiaapplication.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentLoginBinding
import com.example.tutopiaapplication.databinding.FragmentProfileBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.databinding.TitleLayoutBinding

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    private lateinit var titleCardLayoutBinding: TitleCardLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(inflater)

        titleCardLayoutBinding = binding.title

        titleCardLayoutBinding.backImg.setOnClickListener {

        }

        titleCardLayoutBinding.headerTxt.text = "My Profile"

        return binding.root
    }
}