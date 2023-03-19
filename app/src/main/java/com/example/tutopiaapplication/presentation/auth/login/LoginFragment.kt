package com.example.tutopiaapplication.presentation.auth.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tutopiaapplication.BuildConfig
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.login.LoginRequestEntity
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.data.api.model.register.otp.OtpRequestEntity
import com.example.tutopiaapplication.databinding.FragmentLoginBinding
import com.example.tutopiaapplication.domain.model.RegisterEvent
import com.example.tutopiaapplication.domain.model.ValidationRegisterEvent
import com.example.tutopiaapplication.domain.model.login.LoginUserEvent
import com.example.tutopiaapplication.domain.model.login.ValidationLoginUserEvent
import com.example.tutopiaapplication.presentation.auth.login.viewModel.LoginViewModel
import com.example.tutopiaapplication.presentation.auth.otp.view.viewModel.LoginViewModelFactory
import com.example.tutopiaapplication.presentation.auth.register.viewModel.RegisterViewModel
import com.example.tutopiaapplication.presentation.auth.register.viewModel.RegisterViewModelFactory
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.DataStoreUtil
import com.example.tutopiaapplication.utils.Listener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class LoginFragment : Fragment(){

    private lateinit var binding : FragmentLoginBinding

    lateinit var listener: Listener

    @Inject
    lateinit var dataStore: DataStoreUtil

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    var isNavigated = false

    private var requestOtpFlowJob: Job? = null

    private var loginFlowJob: Job? = null


//    private val viewModel by viewModels<RegisterViewModel>()

    lateinit var viewModel: LoginViewModel

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        viewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            dataStore.clearDataStore()
        }

        binding.registerTxt.setOnClickListener {
            if(listener!=null){
                listener.onButtonClicked()
            }
        }

        bundle.putString(Constants.FROM_FRAGMENT,"LoginFragment")

        val androidId = Settings.Secure.getString(
            requireContext().contentResolver,
            Settings.Secure.ANDROID_ID)

        binding.passwordEditTxt.addTextChangedListener {
            viewModel.onEvent(LoginUserEvent.PasswordChanged(it.toString()))
        }

        binding.mobilenoEditTxt.addTextChangedListener {
            viewModel.onEvent(LoginUserEvent.MobileNumberChanged(it.toString()))
        }

        binding.loginBtn.setOnClickListener {
            viewModel.onEvent(LoginUserEvent.LoginUser)
        }

        binding.otpBtn.setOnClickListener {
            viewModel.onEvent(LoginUserEvent.RequestOtp)

//            this@LoginFragment.findNavController().navigate(R.id.action_swipeLoginFragment_to_otpFragment,bundle)
        }

        binding.forgetPasswordTxt.setOnClickListener {
          findNavController().navigate(R.id.action_swipeLoginFragment_to_forgotPasswordFragment,bundle)
        }

        requestOtpFlowJob = viewModel.requestOtpStateFlow.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("resource", "Loading")
                }
                is Resource.Success -> {
                    Log.i("registerResponse", "${resource.data?.data} and ${isNavigated}")

                    bundle.putString(Constants.FROM_FRAGMENT,"RequestOtpFragment")

                    val requestOtpData = resource.data?.data
                    val message = resource.data?.message
                    if (requestOtpData != null && !isNavigated) {

                        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()

                      /*  dataStore.setData(Constants.USER_ID, registerData.userId.toString())
                        dataStore.setData(Constants.OTP, registerData.otp)*/

                        bundle.putInt(Constants.REGISTER_USERID, requestOtpData.userId.toInt())
                        bundle.putString(Constants.REGISTER_OTP, requestOtpData.otp)

                        isNavigated = true
                        findNavController().navigate(
                            R.id.action_swipeLoginFragment_to_otpFragment,
                            bundle
                        )
                    }
                }
                is Resource.Error -> {
                    var errorList = resource.error
                    Log.i("registerErrorResponse", "${errorList} and ${resource.message}")
                    if (errorList != null) {
                        for (error in errorList) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                        }
                    }
                    else if(resource.message!=null){
                        Log.i("registerErrorResponse2", "${resource.message}")
                        val message : String = resource.message
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }.launchIn(lifecycleScope)

        loginFlowJob = viewModel.loginStateFlow.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("resource", "Loading")
                }
                is Resource.Success -> {
                    Log.i("registerResponse", "${resource.data?.data} and ${isNavigated}")

                    val requestOtpData = resource.data?.data
                    val message = resource.data?.message
                    if (requestOtpData != null && !isNavigated) {

                        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()

                        if(requestOtpData.verify_otp==true)
                        {
                            isNavigated = true

                            dataStore.setData(Constants.USER_ID,requestOtpData.userId.toString())

                            bundle.putInt(Constants.REGISTER_USERID, requestOtpData.userId!!.toInt())
                            bundle.putString(Constants.REGISTER_OTP, requestOtpData.otp)

                           findNavController().navigate(R.id.action_swipeLoginFragment_to_otpFragment,bundle)

                        }
                        else if(requestOtpData.verify_otp==false){
                            isNavigated = true

                            dataStore.setData(Constants.ACCESS_TOKEN,
                                requestOtpData.access_token.toString()
                            )

                            bundle.putParcelable(Constants.PROFILE, requestOtpData.profile)

                          findNavController().navigate(R.id.action_swipeLoginFragment_to_homeScreenFragment,bundle)
                        }

                        /*  dataStore.setData(Constants.USER_ID, registerData.userId.toString())
                          dataStore.setData(Constants.OTP, registerData.otp)*/
                    }
                }
                is Resource.Error -> {
                    var errorList = resource.error
                    Log.i("registerErrorResponse", "${errorList} and ${resource.message}")
                    if (errorList != null) {
                        for (error in errorList) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                        }
                    }
                    else if(resource.message!=null){
                        Log.i("registerErrorResponse2", "${resource.message}")
                        val message : String = resource.message
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }.launchIn(lifecycleScope)



        viewModel.validationLoginEvents.onEach { event ->
            when (event) {
                is ValidationLoginUserEvent.Success -> {
                    Log.i("validation", "Success")
                    var requestEntity = LoginRequestEntity(
                        mobile = event.mobileNo,
                        password = event.password,
                        device_token = androidId,
                        version = BuildConfig.VERSION_NAME,
                        platform = Constants.PLATFORM
                    )

                    bundle.putString(Constants.PASSWORD,event.password)
                    bundle.putString(Constants.MOBILE_NUMBER,event.mobileNo)

                    Log.i("LoginRequestEntity", "${requestEntity}")
//                    bundle.putParcelable(Constants.REGISTER_DETAILS,requestEntity)
                    viewModel.loginUser(requestEntity)
                }

                is ValidationLoginUserEvent.Error -> {
//                                binding.passwordTxt.error = null
                    if (
                        event.mobileNoError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.mobileNoError,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else if (event.passwordError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.passwordError,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.validationRequestOtpEvents.onEach { event ->
            when (event) {
                is ValidationLoginUserEvent.Success -> {
                    Log.i("validation", "Success")
                    var requestEntity = OtpRequestEntity(
                        mobile = event.mobileNo
                    )
                    bundle.putString(Constants.MOBILE_NUMBER,event.mobileNo)
                    Log.i("RequestOtpEntity", "${requestEntity}")
//                    bundle.putParcelable(Constants.REGISTER_DETAILS,requestEntity)
                    viewModel.requestOtpUser(requestEntity)
                }

                is ValidationLoginUserEvent.Error -> {
//                                binding.passwordTxt.error = null
                if (
                        event.mobileNoError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.mobileNoError,
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
        loginFlowJob?.cancel()
        requestOtpFlowJob?.cancel()
    }
}