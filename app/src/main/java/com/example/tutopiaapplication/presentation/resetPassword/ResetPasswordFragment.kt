package com.example.tutopiaapplication.presentation.resetPassword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.resetPassword.ResetPasswordRequestEntity
import com.example.tutopiaapplication.databinding.FragmentResetPasswordBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.domain.model.password.ChangePasswordEvent
import com.example.tutopiaapplication.domain.model.password.ValidationChangePasswordEvent
import com.example.tutopiaapplication.presentation.resetPassword.viewModel.ResetPasswordViewModelFactory
import com.example.tutopiaapplication.presentation.resetPassword.viewModel.ResetPasswordViewModel
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.DataStoreUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    lateinit var binding: FragmentResetPasswordBinding

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

    @Inject
    lateinit var dataStore: DataStoreUtil

    @Inject
    lateinit var resetPasswordViewModelFactory: ResetPasswordViewModelFactory

    var isNavigated = false

    private var resetPasswordJob: Job? = null

    lateinit var viewModel: ResetPasswordViewModel

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater)

        viewModel = ViewModelProvider(this, resetPasswordViewModelFactory).get(ResetPasswordViewModel::class.java)

        val otp = arguments?.getString(Constants.REGISTER_OTP)

        val mobile = arguments?.getString(Constants.MOBILE_NUMBER)

        titleLayoutBinding = binding.title

        titleLayoutBinding.userImg.visibility = View.GONE

        titleLayoutBinding.headerTxt.text = "Reset Password"

        titleLayoutBinding.backImg.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.passwordEditText.visibility = View.GONE

        binding.newPasswordEditTxt.addTextChangedListener {
            viewModel.onEvent(ChangePasswordEvent.NewPasswordChanged(it.toString()))
        }

        binding.confirmPasswordEditTxt.addTextChangedListener {
            viewModel.onEvent(ChangePasswordEvent.ConfirmPasswordChanged(it.toString()))
        }

        binding.saveBtn.setOnClickListener {
            viewModel.onEvent(ChangePasswordEvent.SavePassword)
        }

        resetPasswordJob =  viewModel.resetPasswordStateFlow.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("resource", "Loading")
                }
                is Resource.Success -> {
                    Log.i("resetPasswordResponse", "${resource.data?.data} and ${isNavigated}")

                    val resetPasswordData = resource.data?.data
                    val message = resource.data?.message
                    if (resetPasswordData != null && !isNavigated) {

                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                        /*  dataStore.setData(Constants.USER_ID, registerData.userId.toString())
                          dataStore.setData(Constants.OTP, registerData.otp)*/

                        isNavigated = true
                        findNavController().navigate(
                            R.id.action_forgotPasswordFragment_to_swipeLoginFragment,
                            bundle
                        )
                    }
                }
                is Resource.Error -> {
                    val errorList = resource.error
                    Log.i("resetPwdErrorResponse", "${errorList} and ${resource.message}")
                    if (errorList != null) {
                        for (error in errorList) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                        }
                    } else if (resource.message != null) {
                        Log.i("resetPwdErrorResponse2", "${resource.message}")
                        val message: String = resource.message
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.validationChangePasswordEvents.onEach { event ->
            when (event) {
                is ValidationChangePasswordEvent.Success -> {
                    Log.i("validation", "Success")
                    val requestEntity = ResetPasswordRequestEntity(
                        mobile = mobile!!,
                        otp = otp!!,
                        password = event.newPassword,
                        confirm_password = event.confirmPassword
                    )

                 /*   bundle.putString(Constants.PASSWORD,event.password)
                    bundle.putString(Constants.MOBILE_NUMBER,event.mobileNo)*/

                    Log.i("ChangePasswordEntity", "${requestEntity}")
//                    bundle.putParcelable(Constants.REGISTER_DETAILS,requestEntity)
                    viewModel.resetPassword(requestEntity)
                }

                is  ValidationChangePasswordEvent.Error -> {
//                                binding.passwordTxt.error = null
                    if (
                        event.newPasswordError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.newPasswordError,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else if (event.confirmPasswordError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.confirmPasswordError,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }.launchIn(lifecycleScope)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        resetPasswordJob?.cancel()
    }

}