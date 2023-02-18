package com.example.tutopiaapplication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentForgotPasswordBinding
import com.example.tutopiaapplication.databinding.FragmentLoginBinding
import com.example.tutopiaapplication.databinding.FragmentRegisterBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.databinding.TitleLayoutBinding
import com.example.tutopiaapplication.utils.Constants

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding : FragmentForgotPasswordBinding

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentForgotPasswordBinding.inflate(inflater)

        val fromFragment = arguments?.getString(Constants.FROM_FRAGMENT)

        titleLayoutBinding = binding.title

        titleLayoutBinding.headerTxt.text = "Forgot Password"

        titleLayoutBinding.userImg.visibility = View.GONE

        titleLayoutBinding.backImg.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.submitBtn.setOnClickListener {
            if(fromFragment=="ProfileFragment")
            {
                Navigation.findNavController(it).navigate(R.id.action_forgotPasswordFragment_to_profileFragment)
            }
            else{
                Navigation.findNavController(it).navigate(R.id.action_forgotPasswordFragment_to_homeScreenFragment)
            }
        }

        return binding.root
    }

}