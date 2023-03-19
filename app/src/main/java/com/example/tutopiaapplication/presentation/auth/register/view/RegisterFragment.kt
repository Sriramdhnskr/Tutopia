package com.example.tutopiaapplication.presentation.auth.register.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.R.layout
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.register.RegisterRequestEntity
import com.example.tutopiaapplication.databinding.FragmentRegisterBinding
import com.example.tutopiaapplication.domain.model.MappedItem
import com.example.tutopiaapplication.domain.model.RegisterEvent
import com.example.tutopiaapplication.domain.model.ValidationRegisterEvent
import com.example.tutopiaapplication.presentation.auth.adapter.HintAdapter
import com.example.tutopiaapplication.presentation.auth.register.viewModel.RegisterViewModel
import com.example.tutopiaapplication.presentation.auth.register.viewModel.RegisterViewModelFactory
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.DataStoreUtil
import com.example.tutopiaapplication.utils.Listener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment() : Fragment() {

    lateinit var boardAdapter: SpinnerAdapter

    lateinit var classAdapter: SpinnerAdapter

    lateinit var binding: FragmentRegisterBinding

    lateinit var listener: Listener

    private var registerFlowJob: Job? = null

    @Inject
    lateinit var dataStore: DataStoreUtil

    @Inject
    lateinit var registerFactory: RegisterViewModelFactory

    var isNavigated = false

//    private val viewModel by viewModels<RegisterViewModel>()

    lateinit var viewModel: RegisterViewModel

    var boardId: String? = null

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentRegisterBinding.inflate(inflater)

        bundle.putString(Constants.FROM_FRAGMENT,"RegisterFragment")

        viewModel = ViewModelProvider(this, registerFactory).get(RegisterViewModel::class.java)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            dataStore.clearDataStore()
        }

        binding.nameEditTxt.addTextChangedListener {
            viewModel.onEvent(RegisterEvent.NameChanged(it.toString()))
        }

        binding.mobilenoEditTxt.addTextChangedListener {
            viewModel.onEvent(RegisterEvent.MobileNumberChanged(it.toString()))
        }

        /* viewModel.getBoardsList.onEach { resource ->
             when (resource) {
                 is Resource.Loading -> {
                     Log.i("resource", "Loading")
                 }
                 is Resource.Success -> {
                    var boardList : List<String>? = resource.data?.data?.boards?.map { it.board_name }
                 }
                 is Resource.Error -> {
                     Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                 }

             }
         }.launchIn(lifecycleScope)*/

        binding.boardSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    binding.classSpinner.visibility = View.GONE
                } else {
                    binding.classSpinner.visibility = View.VISIBLE

                    boardId = (parent?.getItemAtPosition(position) as MappedItem).id

                    viewModel.onEvent(
                        RegisterEvent.SelectedBoardChanged(
                            boardId?:""
                        )
                    )
                    observeClasses()
                }
//                viewModel.setBoard(parent?.getItemAtPosition(position) as String)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.classSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    var classId = (parent?.getItemAtPosition(position) as MappedItem).id

                    Log.i("classSpinner","${classId}")

                    viewModel.onEvent(
                        RegisterEvent.SelectedClassChanged(
                           classId?:""
                        )
                    )
                }
//                viewModel.setStudentClass(parent?.getItemAtPosition(position) as String)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        lifecycleScope.launchWhenStarted {
            viewModel.toastEvent.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        /*  binding.boardSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
              override fun onItemSelected(
                  parent: AdapterView<*>?,
                  view: View?,
                  position: Int,
                  id: Long
              ) {
                  if (parent != null && position==0) {
                      (parent.getChildAt(0) as TextView).setTextColor(
                          Color.BLUE
                      )
                  }*//*
                if(position==0)
                {
                 binding.classSpinner.visibility  = View.GONE
                }
                else{
                    binding.classSpinner.visibility  = View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })*/

        binding.registerBtn.setOnClickListener {
            isNavigated = false
            viewModel.onEvent(RegisterEvent.Submit)
            /*    bundle.putString(Constants.FROM_FRAGMENT,"RegisterFragment")
                Navigation.findNavController(it).navigate(R.id.action_swipeLoginFragment_to_otpFragment,bundle)*/
        }

       registerFlowJob = viewModel.registrationStateFlow.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("resource", "Loading")
                }
                is Resource.Success -> {
                    Log.i("registerResponse", "${resource.data?.data} and ${isNavigated}")

                    val registerData = resource.data?.data
                    if (registerData != null && !isNavigated) {
                        dataStore.setData(Constants.USER_ID, registerData.userId.toString())
                        dataStore.setData(Constants.OTP, registerData.otp)

                        bundle.putInt(Constants.REGISTER_USERID, registerData.userId)
                        bundle.putString(Constants.REGISTER_OTP, registerData.otp)

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


        observeBoards()

        viewModel.validationEvents.onEach { event ->
            when (event) {
                is ValidationRegisterEvent.Success -> {
                    Log.i("validation", "Success")
                    var requestEntity = RegisterRequestEntity(
                        name = event.name,
                        mobile = event.mobileNo,
                        board_id = event.board,
                        classes = event.className
                    )
                    Log.i("RequestEntity", "${requestEntity}")
                    bundle.putParcelable(Constants.REGISTER_DETAILS,requestEntity)
                    viewModel.register(requestEntity)
                }

                is ValidationRegisterEvent.Error -> {
//                                binding.passwordTxt.error = null
                    if (event.nameError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.nameError,
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (
                        event.mobileNoError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.mobileNoError,
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (
                        event.boardError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.boardError,
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (
                        event.classNameError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.classNameError,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }.launchIn(lifecycleScope)

        binding.loginTxt.setOnClickListener {
            if (listener != null) {
                listener.onButtonClicked()
            }
        }

        return binding.root
    }

    private fun observeBoards() {
        viewModel.getBoardsList.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("resource", "Loading")
                }
                is Resource.Success -> {
                    Log.i("BoardList", "${resource.data}")
                    val spinnerItems = resource.data?.map { it.toMappedItem() } ?: emptyList()

                    spinnerItems.toMutableList().apply {
                        add(0, MappedItem(name = "Select Board"))
                        boardAdapter = HintAdapter(
                            requireContext(),
                            layout.hint_popup_item, R.id.spinnerTxt,
    //                                    arrayListOf("Select Boards", "item1", "item2", "item3")
                            this
                        )

                        binding.boardSpinner.adapter = boardAdapter
                    }


    //                                var boardList : List<String>? = resource.data?.data?.boards?.map { it.board_name }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }

            }
        }.launchIn(lifecycleScope)
    }

    private fun observeClasses() {
        viewModel.getClasses.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("resource", "Loading")
                }
                is Resource.Success -> {
                    Log.i("classesList", "${resource.data}")
                    val classes = resource.data
                    val selectedBoardId = boardId
                    val filteredClasses = classes?.filter { it.boardId == selectedBoardId }

                    Log.i(
                        "ResourceClass",
                        "boardId : ${boardId} and classes : ${filteredClasses}"
                    )

//                    val spinnerItems = resource.data?.map { it.toMappedItem() } ?: emptyList()

                    val spinnerItems = filteredClasses?.map { it.toMappedItem() } ?: emptyList()

                    spinnerItems.toMutableList().apply {
                        add(0, MappedItem(name = "Select Class"))
                        classAdapter = HintAdapter(
                            requireContext(), layout.hint_popup_item, R.id.spinnerTxt,
                            this
                        )

                        binding.classSpinner.adapter = classAdapter
                    }


    //                                var boardList : List<String>? = resource.data?.data?.boards?.map { it.board_name }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }

            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        registerFlowJob?.cancel()
        super.onDestroyView()
    }
}