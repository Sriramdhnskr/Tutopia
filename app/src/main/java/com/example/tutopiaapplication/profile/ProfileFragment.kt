package com.example.tutopiaapplication.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentLoginBinding
import com.example.tutopiaapplication.databinding.FragmentProfileBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.databinding.TitleLayoutBinding
import com.example.tutopiaapplication.utils.Constants

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    val bundle = Bundle()

    private lateinit var titleCardLayoutBinding: TitleCardLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(inflater)

        titleCardLayoutBinding = binding.title

        titleCardLayoutBinding.backImg.setOnClickListener {
                  activity?.onBackPressedDispatcher?.onBackPressed()
        }

        bundle.putString(Constants.FROM_FRAGMENT,"ProfileFragment")

        binding.editBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_completeProfileFragment,bundle)
        }

        binding.changePasswordTxt.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_completeProfileFragment,bundle)
        }



        titleCardLayoutBinding.userImg.visibility = View.GONE

        titleCardLayoutBinding.headerTxt.text = "My Profile"

        return binding.root
    }
}