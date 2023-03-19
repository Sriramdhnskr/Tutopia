package com.example.tutopiaapplication.presentation.forgotPassword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.databinding.FragmentForgotPasswordBinding
import com.example.tutopiaapplication.databinding.FragmentLoginBinding
import com.example.tutopiaapplication.databinding.FragmentRegisterBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.databinding.TitleLayoutBinding
import com.example.tutopiaapplication.domain.model.ValidationResult
import com.example.tutopiaapplication.presentation.auth.register.viewModel.RegisterViewModel
import com.example.tutopiaapplication.presentation.auth.register.viewModel.RegisterViewModelFactory
import com.example.tutopiaapplication.presentation.forgotPassword.viewModel.ForgotPasswordViewModel
import com.example.tutopiaapplication.presentation.forgotPassword.viewModel.ForgotPasswordViewModelFactory
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.DataStoreUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

    val bundle = Bundle()

    private var forgotPasswordFlowJob: Job? = null

    @Inject
    lateinit var dataStore: DataStoreUtil

    @Inject
    lateinit var forgotPasswordViewModelFactory: ForgotPasswordViewModelFactory

    var isNavigated = false

    lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentForgotPasswordBinding.inflate(inflater)

        viewModel = ViewModelProvider(this, forgotPasswordViewModelFactory).get(ForgotPasswordViewModel::class.java)

        val fromFragment = arguments?.getString(Constants.FROM_FRAGMENT)

        bundle.putString(Constants.FROM_FRAGMENT, "ForgotPasswordFragment")

        titleLayoutBinding = binding.title

        titleLayoutBinding.headerTxt.text = "Forgot Password"

        titleLayoutBinding.userImg.visibility = View.GONE

        titleLayoutBinding.backImg.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.submitBtn.setOnClickListener {
            var mobileNumber = binding.mobileNoEditTxt.text.toString()
            var errorMessage : String? = null
            if(mobileNumber.isEmpty())
            {
                    errorMessage = "Please enter your mobile no"
                Toast.makeText(requireContext(),errorMessage,Toast.LENGTH_LONG).show()
            }
            else if(mobileNumber.length<10)
            {
                    errorMessage = "Please enter your 10 digit mobile no"
                Toast.makeText(requireContext(),errorMessage,Toast.LENGTH_LONG).show()
            }
            else{
                val requestEntity = OtpRequestEntity(
                    mobile = mobileNumber
                )
                viewModel.forgotPassword(requestEntity)
            }

           /* if (fromFragment == "ProfileFragment") {
                Navigation.findNavController(it)
                    .navigate(R.id.action_forgotPasswordFragment_to_profileFragment)
            } else {
                Navigation.findNavController(it)
                    .navigate(R.id.action_forgotPasswordFragment_to_homeScreenFragment)
            }*/
        }

        forgotPasswordFlowJob = viewModel.forgotPasswordStateFlow.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("resource", "Loading")
                }
                is Resource.Success -> {
                    Log.i("forgotPasswordResponse", "${resource.data?.data} and ${isNavigated}")

                    val forgotPasswordData = resource.data?.data
                    val message = resource.data?.message
                    if (forgotPasswordData != null && !isNavigated) {

//                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                        /*  dataStore.setData(Constants.USER_ID, registerData.userId.toString())
                          dataStore.setData(Constants.OTP, registerData.otp)*/

                        bundle.putString(Constants.REGISTER_OTP, forgotPasswordData.otp)

                        isNavigated = true
                        findNavController().navigate(
                            R.id.action_forgotPasswordFragment_to_otpFragment,
                            bundle
                        )
                    }
                }
                is Resource.Error -> {
                    var errorList = resource.error
                    Log.i("forgotPwdErrorResponse", "${errorList} and ${resource.message}")
                    if (errorList != null) {
                        for (error in errorList) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                        }
                    } else if (resource.message != null) {
                        Log.i("forgotPwdErrorResponse2", "${resource.message}")
                        val message: String = resource.message
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }.launchIn(lifecycleScope)


        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        forgotPasswordFlowJob?.cancel()
    }
}