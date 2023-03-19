package com.example.tutopiaapplication.presentation.changePassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentChangePasswordBinding
import com.example.tutopiaapplication.databinding.FragmentForgotPasswordBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    private lateinit var binding : FragmentChangePasswordBinding

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentChangePasswordBinding.inflate(inflater)

        return binding.root
    }
}