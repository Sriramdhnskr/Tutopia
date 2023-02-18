package com.example.tutopiaapplication.login

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentLoginBinding
import com.example.tutopiaapplication.databinding.FragmentOtpBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.databinding.TitleLayoutBinding
import com.example.tutopiaapplication.model.ProductDetails
import com.example.tutopiaapplication.utils.Constants

class OtpFragment : Fragment() {

    private lateinit var binding : FragmentOtpBinding

    val bundle  = Bundle()

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentOtpBinding.inflate(inflater)

        val fromFragment = arguments?.getString(Constants.FROM_FRAGMENT)

        titleLayoutBinding = binding.title

        titleLayoutBinding.headerTxt.text = "Otp".uppercase()

        titleLayoutBinding.backImg.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        bundle.putString(Constants.FROM_FRAGMENT,"OtpFragment")

        binding.verifyBtn.setOnClickListener {
            if(fromFragment=="RegisterFragment"){
                Navigation.findNavController(it).navigate(R.id.action_otpFragment_to_completeProfileFragment,bundle)
            }
            else if(fromFragment=="LoginFragment"){
                Navigation.findNavController(it).navigate(R.id.action_otpFragment_to_completeProfileFragment,bundle)
            }
        }

        titleLayoutBinding.userImg.visibility = View.GONE

        return binding.root
    }

}