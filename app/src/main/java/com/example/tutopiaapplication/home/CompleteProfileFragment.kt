package com.example.tutopiaapplication.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentCompleteProfileBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.databinding.TitleLayoutBinding
import com.example.tutopiaapplication.login.adapter.HintAdapter
import com.example.tutopiaapplication.utils.Constants

class CompleteProfileFragment : Fragment() {

    lateinit var binding : FragmentCompleteProfileBinding

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

    lateinit var spinnerAdapter : SpinnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCompleteProfileBinding.inflate(inflater)

        titleLayoutBinding = binding.title

        titleLayoutBinding.headerTxt.text = "Complete Profile"

        titleLayoutBinding.userImg.visibility = View.GONE

        val fromFragment = arguments?.getString(Constants.FROM_FRAGMENT)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val drawerLayout = activity!!.findViewById<DrawerLayout>(R.id.drawer_layout)

                if(fromFragment=="OtpFragment"){
                    findNavController().navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)
                }
                else{
                    findNavController().popBackStack()
                }
            }
        })

        if(fromFragment=="ProfileFragment"){
           binding.skipBtn.visibility  =View.GONE
        }
        else{
            binding.skipBtn.visibility  =View.VISIBLE
        }

        titleLayoutBinding.backImg.setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)

            if(fromFragment=="OtpFragment"){
                Navigation.findNavController(it).navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)
            }
            else{
                Navigation.findNavController(it).popBackStack()
            }
        }

        spinnerAdapter = HintAdapter(requireContext(),R.layout.hint_popup_item,R.id.spinnerTxt,
            arrayListOf("Select Name","item1","item2","item3")
        )

        binding.skipBtn.setOnClickListener {

            if(fromFragment=="OtpFragment"){
                Navigation.findNavController(it).navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)
            }
        }

        binding.registerBtn.setOnClickListener {
            if(fromFragment=="OtpFragment"){
                Navigation.findNavController(it).navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)
            }
            else if(fromFragment=="ProfileFragment"){
                Navigation.findNavController(it).navigate(R.id.action_completeProfileFragment_to_profileFragment)
            }
        }

        binding.schoolNameSpinner.adapter = spinnerAdapter

        return binding.root
    }

}