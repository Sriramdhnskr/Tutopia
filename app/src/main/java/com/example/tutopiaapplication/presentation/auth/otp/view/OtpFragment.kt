package com.example.tutopiaapplication.presentation.auth.otp.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.Secure
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.Profile
import com.example.tutopiaapplication.data.api.model.login.verifyLogin.VerifyLoginRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.data.api.model.register.verifyOtp.VerifyOtpRequestEntity
import com.example.tutopiaapplication.databinding.FragmentOtpBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.presentation.auth.otp.viewModel.OtpViewModel
import com.example.tutopiaapplication.presentation.auth.otp.viewModel.OtpViewModelFactory
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.DataStoreUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding

    private var forgotPasswordFlowJob: Job? = null

    val bundle = Bundle()

    var isNavigated = false

    private var verifyLoginFlowJob: Job? = null

    private var resendOtpFlowJob: Job? = null

    private var requestOtpFlowJob: Job? = null

    lateinit var handler: Handler

    @Inject
    lateinit var dataStore: DataStoreUtil

    @Inject
    lateinit var viewModelFactory: OtpViewModelFactory

    lateinit var viewModel: OtpViewModel

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentOtpBinding.inflate(inflater)

        viewModel = ViewModelProvider(this, viewModelFactory).get(OtpViewModel::class.java)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_otpFragment_to_swipeLoginFragment)
        }

        val userId = arguments?.getInt(Constants.REGISTER_USERID)

        val profileDetails = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable<Profile>(
                Constants.PROFILE,
                Profile::class.java
            )
        } else {
            arguments?.getParcelable<Profile>(Constants.PROFILE)
        }

        val androidId = Secure.getString(
            requireContext().contentResolver,
            Secure.ANDROID_ID
        )

        var otp = arguments?.getString(Constants.REGISTER_OTP)

        setOtpData(otp)

        Log.i("User", "${userId}")

        val registerDetails = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable<RegisterRequestEntity>(
                Constants.REGISTER_DETAILS,
                RegisterRequestEntity::class.java
            )
        } else {
            arguments?.getParcelable<RegisterRequestEntity>(Constants.REGISTER_DETAILS)
        }

        val fromFragment = arguments?.getString(Constants.FROM_FRAGMENT)

        val mobileNumber = arguments?.getString(Constants.MOBILE_NUMBER)

        val password = arguments?.getString(Constants.PASSWORD)

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
        } else if (fromFragment == "LoginFragment") {
            binding.otplabelTxt.text = buildString {
                append("Please enter the OTP which has been sent to +91 ")
                append(mobileNumber)
            }
        }

        var secondsLeft = 30

        handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (secondsLeft > 0) {
                    val minutes = secondsLeft / 60
                    val seconds = secondsLeft % 60
                    val timeString = String.format("%02d:%02d", minutes, seconds)
                    binding.resendTxt.visibility = View.GONE
                    binding.sendDescTxt.text = timeString
                    secondsLeft--
                    handler.postDelayed(this, 1000)
                } else {
                    binding.resendTxt.visibility = View.VISIBLE
                    binding.sendDescTxt.text =
                        resources.getString(R.string.didn_t_receive_otp_resend)
                }
            }
        }
        handler.post(runnable)

        binding.verifyBtn.setOnClickListener {

            if (fromFragment == "RegisterFragment" || fromFragment == "RequestOtpFragment") {

                val otpString =
                    "${binding.otpEditBox1.text}${binding.otpEditBox2.text}${binding.otpEditBox3.text}${binding.otpEditBox4.text}"

                var requestOtpEntity = VerifyOtpRequestEntity(
                    user_id = userId?.toInt(),
                    otp = otpString,
                    device_token = androidId,
                    platform = Constants.PLATFORM,
                    fcm_token = ""
                )
                Log.i("RequestOtpEntity", "${requestOtpEntity}")
//            bundle.putParcelable(Constants.REGISTER_DETAILS,requestEntity)
                viewModel.verifyOtp(requestOtpEntity)
            } else if (fromFragment == "LoginFragment") {
                var requestEntity = VerifyLoginRequestEntity(
                    mobile = mobileNumber ?: "",
                    password = password ?: "",
                    device_token = androidId,
                    platform = Constants.PLATFORM,
                    otp = Constants.REGISTER_OTP
                )
                viewModel.verifyLogin(requestEntity)
            } else if (fromFragment == "ForgotPasswordFragment") {
                if (binding.otpEditBox1.text.isEmpty() &&
                    binding.otpEditBox2.text.isEmpty() &&
                    binding.otpEditBox3.text.isEmpty() &&
                    binding.otpEditBox4.text.isEmpty()
                ) {

                } else {
//                    findNavController().navigate(R.id.ac)
                }
            }

            binding.resendTxt.setOnClickListener {

                /*var requestEntity = RegisterRequestEntity(
                name = registerDetails?.name,
                mobile = registerDetails?.mobile,
                board_id = registerDetails?.board_id,
                classes = registerDetails?.classes
            )*/

                if (fromFragment == "RegisterFragment") {
                    if (registerDetails != null) {
                        Log.i("RequestEntity", "${registerDetails}")
                        bundle.putParcelable(Constants.REGISTER_DETAILS, registerDetails)

                        viewModel.register(registerDetails)
                    }
                } else if (fromFragment == "LoginFragment" || fromFragment == "RequestOtpFragment") {

                    val requestEntity = OtpRequestEntity(
                        mobile = mobileNumber
                    )

                    viewModel.resendOtp(requestEntity)
                } else if (fromFragment == "ForgotPasswordFragment") {

                    val requestEntity = OtpRequestEntity(
                        mobile = mobileNumber
                    )

                    viewModel.forgotPassword(requestEntity)
                }


                /* val otpRequestEntity = OtpRequestEntity(
                 country_code = "+91",
                 mobile = registerDetails?.mobile
             )
             Log.i("RequestOtpEntity", "${otpRequestEntity}")
 //            bundle.putParcelable(Constants.REGISTER_DETAILS,requestEntity)
             viewModel.resendOtp(otpRequestEntity)*/
            }

            titleLayoutBinding.userImg.visibility = View.GONE

            resendOtpFlowJob = viewModel.resendOtpStateFlow.onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.i("resource", "Loading")
                    }
                    is Resource.Success -> {
                        Log.i(
                            "resendResponse",
                            "${resource.data?.data} and ${resource.data?.message}"
                        )

                        val registerData = resource.data?.data
                        if (registerData != null) {

                            setOtpData(registerData.otp)

                            Toast.makeText(
                                requireContext(),
                                "${resource.data.message}",
                                Toast.LENGTH_LONG
                            )
                                .show()

                            dataStore.setData(Constants.USER_ID, registerData.userId.toString())

                            secondsLeft = 30
                            handler.removeCallbacks(runnable)
                            handler.post(runnable)

                            /*   dataStore.setData(Constants.USER_ID, registerData.userId.toString())
                           dataStore.setData(Constants.OTP, registerData.otp)

                           bundle.putInt(Constants.REGISTER_USERID, registerData.userId)
                           bundle.putString(Constants.REGISTER_OTP, registerData.otp)*/
                        }
                    }
                    is Resource.Error -> {
                        var errorList = resource.error
                        Log.i("resendErrorResponse", "${errorList} and ${resource.message}")
                        if (errorList != null) {
                            for (error in errorList) {
                                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                            }
                        } else if (resource.message != null) {
                            Log.i("resendErrorResponse2", "${resource.message}")
                            val message: String = resource.message
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }.launchIn(lifecycleScope)


//        viewModel.requestOtpStateFlow.onEach { resource ->
//            when (resource) {
//                is Resource.Loading -> {
//                    Log.i("resource", "Loading")
//                }
//                is Resource.Success -> {
//                    Log.i("ResendResponse", "${resource.data?.data}")
//
//                    val otpResponseData = resource.data?.data
//                    if (otpResponseData != null) {
//
//                        Toast.makeText(requireContext(),resource.data.message, Toast.LENGTH_LONG).show()
//
//                        dataStore.setData(Constants.USER_ID, otpResponseData.userId)
//
//                        secondsLeft = 30
//                        handler.removeCallbacks(runnable)
//                        handler.post(runnable)
//                    }
//                }
//                is Resource.Error -> {
//                    var errorList = resource.error
//                    Log.i("registerErrorResponse", "${errorList} and ${resource.message}")
//                    if (errorList != null) {
//                        for (error in errorList) {
//                            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
//                        }
//                    }
//                    else if(resource.message!=null){
//                        Log.i("registerErrorResponse2", "${resource.message}")
//                        val message : String = resource.message
//                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
//                    }
//                }
//
//            }
//        }.launchIn(lifecycleScope)

            requestOtpFlowJob = viewModel.requestOtpStateFlow.onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.i("resource", "Loading")
                    }
                    is Resource.Success -> {
                        Log.i("verifyLoginResponse", "${resource.data?.data}")

                        val resendOtpResponse = resource.data?.data
                        if (resendOtpResponse != null && !isNavigated) {

                            setOtpData(resendOtpResponse.otp)

                            dataStore.setData(
                                Constants.USER_ID,
                                resendOtpResponse.userId
                            )
/*
                        bundle.putString(
                            Constants.USER_ACCESS_TOKEN,
                            verifyLoginResponse.access_token
                        )*/

                            secondsLeft = 30
                            handler.removeCallbacks(runnable)
                            handler.post(runnable)

//                        if (fromFragment == "RegisterFragment") {
//                            findNavController()
//                                .navigate(
//                                    R.id.action_otpFragment_to_completeProfileFragment,
//                                    bundle
//                                )
//                        } else if (fromFragment == "LoginFragment") {
//                        }
                        }
                    }

                    is Resource.Error -> {
                        var errorList = resource.error
                        Log.i("registerErrorResponse", "${errorList} and ${resource.message}")
                        if (errorList != null) {
                            for (error in errorList) {
                                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                            }
                        } else if (resource.message != null) {
                            Log.i("registerErrorResponse2", "${resource.message}")
                            val message: String = resource.message
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }.launchIn(lifecycleScope)

            verifyLoginFlowJob = viewModel.verifyLoginStateFlow.onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.i("resource", "Loading")
                    }
                    is Resource.Success -> {
                        Log.i("verifyLoginResponse", "${resource.data?.data}")

                        val verifyLoginResponse = resource.data?.data
                        if (verifyLoginResponse != null && !isNavigated) {

                            dataStore.setData(
                                Constants.ACCESS_TOKEN,
                                verifyLoginResponse.access_token
                            )

                            bundle.putString(
                                Constants.USER_ACCESS_TOKEN,
                                verifyLoginResponse.access_token
                            )
                            bundle.putParcelable(Constants.PROFILE, verifyLoginResponse.profile)

                            isNavigated = true

//                        if (fromFragment == "RegisterFragment") {
//                            findNavController()
//                                .navigate(
//                                    R.id.action_otpFragment_to_completeProfileFragment,
//                                    bundle
//                                )
//                        } else if (fromFragment == "LoginFragment") {
                            findNavController()
                                .navigate(
                                    R.id.action_otpFragment_to_completeProfileFragment,
                                    bundle
                                )
//                        }
                        }
                    }

                    is Resource.Error -> {
                        var errorList = resource.error
                        Log.i("registerErrorResponse", "${errorList} and ${resource.message}")
                        if (errorList != null) {
                            for (error in errorList) {
                                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                            }
                        } else if (resource.message != null) {
                            Log.i("registerErrorResponse2", "${resource.message}")
                            val message: String = resource.message
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }.launchIn(lifecycleScope)


            viewModel.verifyOtpStateFlow.onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.i("resource", "Loading")
                    }
                    is Resource.Success -> {
                        Log.i("otpResponse", "${resource.data?.data}")

                        val otpResponseData = resource.data?.data
                        if (otpResponseData != null && !isNavigated) {

                            dataStore.setData(Constants.ACCESS_TOKEN, otpResponseData.access_token)

                            bundle.putString(
                                Constants.USER_ACCESS_TOKEN,
                                otpResponseData.access_token
                            )
                            bundle.putParcelable(Constants.PROFILE, otpResponseData.profile)

                            isNavigated = true


//                        if (fromFragment == "RegisterFragment") {
//                            findNavController()
//                                .navigate(
//                                    R.id.action_otpFragment_to_completeProfileFragment,
//                                    bundle
//                                )
//                        } else if (fromFragment == "LoginFragment") {
                            findNavController()
                                .navigate(
                                    R.id.action_otpFragment_to_completeProfileFragment,
                                    bundle
                                )
//                        }
                        }
                    }

                    is Resource.Error -> {
                        var errorList = resource.error
                        Log.i("registerErrorResponse", "${errorList} and ${resource.message}")
                        if (errorList != null) {
                            for (error in errorList) {
                                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                            }
                        } else if (resource.message != null) {
                            Log.i("registerErrorResponse2", "${resource.message}")
                            val message: String = resource.message
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }.launchIn(lifecycleScope)

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

                            setOtpData(forgotPasswordData.otp)

//                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                            /*  dataStore.setData(Constants.USER_ID, registerData.userId.toString())
                          dataStore.setData(Constants.OTP, registerData.otp)*/
                            bundle.putString(Constants.REGISTER_OTP, forgotPasswordData.otp)

                            secondsLeft = 30
                            handler.removeCallbacks(runnable)
                            handler.post(runnable)

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
        }

        return binding.root
    }

    private fun setOtpData(otp: String?) {
        val chars = otp?.toCharArray()

        if (chars?.size == 4) {
            binding.otpEditBox1.setText(chars.getOrNull(0)?.toString())
            binding.otpEditBox2.setText(chars.getOrNull(1)?.toString())
            binding.otpEditBox3.setText(chars.getOrNull(2)?.toString())
            binding.otpEditBox4.setText(chars.getOrNull(3)?.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        resendOtpFlowJob?.cancel()
        requestOtpFlowJob?.cancel()
        verifyLoginFlowJob?.cancel()
        handler.removeCallbacksAndMessages(null)
    }
}