package com.example.tutopiaapplication.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import androidx.navigation.Navigation
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentCompleteProfileBinding
import com.example.tutopiaapplication.databinding.TitleLayoutBinding
import com.example.tutopiaapplication.login.adapter.HintAdapter

class CompleteProfileFragment : Fragment() {

    lateinit var binding : FragmentCompleteProfileBinding

    lateinit var spinnerAdapter : SpinnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCompleteProfileBinding.inflate(inflater)

        binding.headerTxt.text = "Complete Profile"

        spinnerAdapter = HintAdapter(requireContext(),binding.schoolNameSpinner, R.layout.hint_popup_item,R.id.spinnerTxt,
            arrayListOf("Select Board","item1","item2","item3")
        )

        binding.skipBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_completeProfileFragment_to_homePageActivity)
        }

        binding.registerBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_completeProfileFragment_to_homePageActivity)
        }

        binding.schoolNameSpinner.adapter = spinnerAdapter

        return binding.root
    }

}