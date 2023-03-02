package com.example.tutopiaapplication.presentation.auth.login

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.databinding.FragmentOtpBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.model.SubjectDetails
import com.example.tutopiaapplication.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding

    val bundle = Bundle()

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentOtpBinding.inflate(inflater)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            findNavController().popBackStack()
        }

        val registerDetails = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable<RegisterRequestEntity>(
                Constants.REGISTER_DETAILS,
                RegisterRequestEntity::class.java
            )
        } else {
            arguments?.getParcelable<RegisterRequestEntity>(Constants.REGISTER_DETAILS)
        }

        val fromFragment = arguments?.getString(Constants.FROM_FRAGMENT)

        titleLayoutBinding = binding.title

        titleLayoutBinding.headerTxt.text = "Otp".uppercase()

        titleLayoutBinding.backImg.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        bundle.putString(Constants.FROM_FRAGMENT, "OtpFragment")

        if (registerDetails != null) {
            binding.otplabelTxt.text = buildString {
                append("Please enter the OTP which has been sent to +91 ")
                append(registerDetails.mobile)
            }
        }

        binding.verifyBtn.setOnClickListener {
            if (fromFragment == "RegisterFragment") {
                Navigation.findNavController(it)
                    .navigate(R.id.action_otpFragment_to_completeProfileFragment, bundle)
            } else if (fromFragment == "LoginFragment") {
                Navigation.findNavController(it)
                    .navigate(R.id.action_otpFragment_to_completeProfileFragment, bundle)
            }
        }

        titleLayoutBinding.userImg.visibility = View.GONE

        return binding.root
    }

}