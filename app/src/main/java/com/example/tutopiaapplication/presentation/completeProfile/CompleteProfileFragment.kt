package com.example.tutopiaapplication.presentation.completeProfile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.core.util.Resource
import com.example.tutopiaapplication.data.api.model.updateProfile.UpdateProfileFormRequest
import com.example.tutopiaapplication.databinding.FragmentCompleteProfileBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.domain.model.*
import com.example.tutopiaapplication.presentation.completeProfile.adapter.CompleteProfileHintAdapter
import com.example.tutopiaapplication.presentation.completeProfile.viewModel.CompleteProfileViewModel
import com.example.tutopiaapplication.presentation.completeProfile.viewModel.CompleteProfileViewModelFactory
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.DataStoreUtil
import com.example.tutopiaapplication.utils.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


@AndroidEntryPoint
class CompleteProfileFragment : Fragment(), ItemClickListener {

    lateinit var binding: FragmentCompleteProfileBinding

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

//    lateinit var spinnerAdapter : SpinnerAdapter

    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val GALLERY_PERMISSION_REQUEST_CODE = 200

    lateinit var schoolAdapter: CompleteProfileHintAdapter

    private var updateProfileFlowJob: Job? = null

    @Inject
    lateinit var dataStore: DataStoreUtil

    lateinit var school: SchoolMappedItem

    lateinit var spinnerItems: List<SchoolMappedItem>

    var pinCode: String? = null

    @Inject
    lateinit var completeProfileViewModelFactory: CompleteProfileViewModelFactory

    var isNavigated = false

//    private val viewModel by viewModels<RegisterViewModel>()

    lateinit var viewModel: CompleteProfileViewModel

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCompleteProfileBinding.inflate(inflater)

        spinnerItems = emptyList()

        viewModel = ViewModelProvider(
            this,
            completeProfileViewModelFactory
        ).get(CompleteProfileViewModel::class.java)

        titleLayoutBinding = binding.title

        titleLayoutBinding.headerTxt.text = "Complete Profile"

        titleLayoutBinding.userImg.visibility = View.GONE

        val fromFragment = arguments?.getString(Constants.FROM_FRAGMENT)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val drawerLayout = activity!!.findViewById<DrawerLayout>(R.id.drawer_layout)

                    if (fromFragment == "OtpFragment") {
                        findNavController().navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)
                    } else {
                        findNavController().popBackStack()
                    }
                }
            })

//        }

        binding.schoolPincodeEditTxt.addTextChangedListener {
            binding.schoolNameSpinner.setSelection(0)
            viewModel.onEvent(CompleteProfileEvent.PinCodeChanged(it.toString()))
        }

        binding.passwordEditTxt.addTextChangedListener {
            viewModel.onEvent(CompleteProfileEvent.PasswordChanged(it.toString()))
        }

        binding.confirmPasswordEditTxt.addTextChangedListener {
            viewModel.onEvent(CompleteProfileEvent.ConfirmPasswordChanged(it.toString()))
        }

        binding.profileImageCardView.setOnClickListener {
            showImageChooserDialog()
        }



        spinnerItems.toMutableList().apply {
            add(0, SchoolMappedItem(name = "Select School", id = "0"))
            schoolAdapter = CompleteProfileHintAdapter(
                requireContext(),
                R.layout.hint_popup_item, R.id.spinnerTxt,
                pincodeEditText = binding.schoolPincodeEditTxt,
                //                                    arrayListOf("Select Boards", "item1", "item2", "item3")
                items = this,
                listener = this@CompleteProfileFragment
            )
//            schoolAdapter.setData(true)
//            schoolAdapter.notifyDataSetChanged()

            binding.schoolNameSpinner.adapter = schoolAdapter

        }

        binding.schoolNameSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Do nothing
//                     viewModel.onEvent(CompleteProfileEvent.SchoolChanged)

                    if (position != 0) {
                        school = (parent?.getItemAtPosition(position) as SchoolMappedItem)

                        viewModel.onEvent(
                            CompleteProfileEvent.SelectedSchoolChanged(
                                schoolCode = school.code!!,
                                schoolName = school.name
                            )
                        )
                        Log.i("Tagger", "spinner: ${position} and ${school}")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }

        /*   binding.schoolNameSpinner.setOnTouchListener(OnTouchListener { view, motionEvent ->
               Toast.makeText(requireContext(), "CLICK", Toast.LENGTH_SHORT).show()
               true
           })*/

        /* binding.schoolNameSpinner.setOnClickListener {
             val pincode = binding.schoolPincodeEditTxt.text.toString()
             if (pincode.isEmpty()) {
                 Toast.makeText(context, "Pincode is empty", Toast.LENGTH_SHORT).show()
                 binding.schoolNameSpinner.isClickable = false
             } else {
                 binding.schoolNameSpinner.isClickable = true
             }
         }*/

        /*binding.schoolNameSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    *//*     if (pinCode.isNullOrEmpty()) {
     //                        school = (parent?.getItemAtPosition(position) as SchoolMappedItem)

                             Toast.makeText(requireContext(), "Toaster", Toast.LENGTH_LONG).show()
                             binding.schoolNameSpinner.setSelection(0)
                             binding.schoolNameSpinner.performClick()

                             viewModel.onEvent(
                                 CompleteProfileEvent.SelectedSchoolChanged(
                                     schoolCode = school.code!!,
                                     schoolName = school.name
                                 )
                             )
                         } *//**//**//**//*else {
//                    }*//*

                    val selectedPincode = binding.schoolPincodeEditTxt.text.toString()
                    if (selectedPincode.isEmpty()) {
                        Toast.makeText(requireContext(), "Pincode is empty", Toast.LENGTH_SHORT)
                            .show()
                        binding.schoolNameSpinner.setSelection(0) // select hint item
//                        binding.schoolNameSpinner.performClick() // dismiss spinner dropdown
                    } else {
                        spinnerItems.toMutableList().apply {
                            add(0, SchoolMappedItem(name = "Select School", id = "0"))
                            add(SchoolMappedItem(name = "vivek", id = "2"))
                            add(SchoolMappedItem(name = "sethu", id = "4"))
                            add(SchoolMappedItem(name = "bhaskar", id = "5"))
                            schoolAdapter = CompleteProfileHintAdapter(
                                requireContext(),
                                R.layout.hint_popup_item, R.id.spinnerTxt,
                                this
                            )
                            binding.schoolNameSpinner.adapter = schoolAdapter

                        }
                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }*/

        viewModel.schoolValidationEvents.onEach { event ->
            when (event) {
                is ValidationSchoolNameEvent.Success -> {
                    Log.i("validationSchool", "Success")

                    pinCode = event.pinCode
                    observeSchools(pinCode)
                }

                is ValidationSchoolNameEvent.Error -> {
//                                binding.passwordTxt.error = null
                    if (event.pinCodeError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.pinCodeError,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.validationEvents.onEach { event ->
            when (event) {
                is ValidationCompleteProfileEvent.Success -> {
                    Log.i("validation", "Success")
                    var requestProfile = UpdateProfileFormRequest(
                        pinCode = event.pinCode,
                        schoolCode = event.schoolCode,
                        schoolName = event.schoolName,
                        password = event.password,
                        confirmPassword = event.confirmPassword,
                        profileImage = event.profileImage
                    )
                    Log.i("RequestProfile", "${requestProfile}")
//                    bundle.putParcelable(Constants.PROFILE_DETAILS, requestProfile)
                    viewModel.updateProfile(requestProfile)
                }

                is ValidationCompleteProfileEvent.Error -> {
//                                binding.passwordTxt.error = null
                    if (event.pinCodeError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.pinCodeError,
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (
                        event.schoolError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.schoolError,
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (
                        event.passwordError != null
                    ) {
                        Toast.makeText(
                            requireContext(),
                            event.passwordError,
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (
                        event.confirmPasswordError != null
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

        updateProfileFlowJob = viewModel.completeProfileStateFlow.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("resource", "Loading")
                }
                is Resource.Success -> {
                    Log.i("profileResponse", "${resource.data?.data} and ${isNavigated}")

                    val registerData = resource.data?.data
                    if (registerData != null && !isNavigated) {
                        var profile = resource.data.data.profile
                        var message = resource.data.message
                        bundle.putParcelable(Constants.PROFILE_DETAILS, profile)

                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

                        isNavigated = true
                        if (fromFragment == "OtpFragment") {
                            findNavController()
                                .navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)
                        } else if (fromFragment == "ProfileFragment") {
                            findNavController()
                                .navigate(R.id.action_completeProfileFragment_to_profileFragment)
                        }

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


        if (fromFragment == "ProfileFragment") {
            binding.skipBtn.visibility = View.GONE
        } else {
            binding.skipBtn.visibility = View.VISIBLE
        }

        titleLayoutBinding.backImg.setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)

            if (fromFragment == "OtpFragment") {
                Navigation.findNavController(it)
                    .navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }

        /*spinnerAdapter = HintAdapter(
            requireContext(), R.layout.hint_popup_item, R.id.spinnerTxt,
            arrayListOf("Select Name","item1","item2","item3")
        )*/

        binding.skipBtn.setOnClickListener {

            if (fromFragment == "OtpFragment") {
                Navigation.findNavController(it)
                    .navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)
            }
        }

        binding.registerBtn.setOnClickListener {
            viewModel.onEvent(CompleteProfileEvent.Submit)
            /* if (fromFragment == "OtpFragment") {
                 Navigation.findNavController(it)
                     .navigate(R.id.action_completeProfileFragment_to_homeScreenFragment)
             } else if (fromFragment == "ProfileFragment") {
                 Navigation.findNavController(it)
                     .navigate(R.id.action_completeProfileFragment_to_profileFragment)
             }*/
        }

//        binding.schoolNameSpinner.adapter = spinnerAdapter

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> {
                    // Get image from gallery
                    val selectedImage = data?.data
                    if (selectedImage != null) {

                        val cameraImageFile = uriToFile(selectedImage)

                        viewModel.onEvent(CompleteProfileEvent.ImageFileChanged(cameraImageFile))

                        Glide.with(requireContext())
                            .load(selectedImage)
                            .into(binding.profileImageView)

                        // Pass the image file to the ViewModel
                    }
                }
                0 -> {
                    // Get image from camera
                    val imageBitmap = data?.extras?.get("data") as Bitmap

                    val galleryImageFile = saveBitmapToFile(imageBitmap)

                    viewModel.onEvent(CompleteProfileEvent.ImageFileChanged(galleryImageFile))

                    Glide.with(requireContext())
                        .load(imageBitmap)
                        .into(binding.profileImageView)

                    // Pass the image file to the ViewModel
                }
            }
        }
    }

    private fun observeSchools(pinCode: String?) {
        viewModel.getSchoolsList.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("resource", "Loading")
                }
                is Resource.Success -> {
                    Log.i("SchoolsList", "${resource.data}")

                    val schools = resource.data

                    val filteredSchools = schools?.filter { it.pincode == pinCode }

                    spinnerItems = filteredSchools?.map { it.toSchool() } ?: emptyList()

                    schoolAdapter.clear()

                    spinnerItems.toMutableList().apply {
                        add(0, SchoolMappedItem(name = "Select School", id = "0"))
                        /*  schoolAdapter = CompleteProfileHintAdapter(
                              requireContext(),
                              R.layout.hint_popup_item, R.id.spinnerTxt,
                              pincodeEditText = binding.schoolPincodeEditTxt,
                              //                                    arrayListOf("Select Boards", "item1", "item2", "item3")
                              items = this,
                              listener = this@CompleteProfileFragment
                          )*/

//                        binding.schoolNameSpinner.adapter = schoolAdapter

                        schoolAdapter.addAll(this)
                    }
//                        schoolAdapter.setData(false)

                    schoolAdapter.notifyDataSetChanged()

                    binding.schoolNameSpinner.performClick()

                    Log.i("SchoolsFilteredList", "${spinnerItems}")


                    //                                var boardList : List<String>? = resource.data?.data?.boards?.map { it.board_name }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }

            }
        }.launchIn(lifecycleScope)
    }

    override fun onButtonClicked(model: Any?, pos: Int) {
        if (model is SchoolMappedItem?) {
//            var pincode = binding.schoolPincodeEditTxt.text.toString()
            var errorMessage: String? = null
            if (binding.schoolPincodeEditTxt.text.isNullOrEmpty()) {
                errorMessage = "Please enter school pincode"
            } else if (binding.schoolPincodeEditTxt.text.length < 6) {
                errorMessage = "Please enter 6 digit valid pincode"
            } else if (!binding.schoolPincodeEditTxt.text.startsWith("7")) {
                errorMessage = "Please enter pincode which starting with 7"
            } else {
                errorMessage = null
                observeSchools(binding.schoolPincodeEditTxt.text.toString())
            }
            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

            Log.i(
                "AdapterClickEvent",
                "spinner: ${model} and check : ${binding.schoolPincodeEditTxt.text.startsWith("7")}pincode : ${binding.schoolPincodeEditTxt.text} and errormessage : ${errorMessage} and edittext : ${binding.schoolPincodeEditTxt.text}  and position : ${pos}"
            )
        }
    }

    private fun showImageChooserDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Action")

        // Add options to the dialog
        val options = arrayOf("Gallery", "Camera")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    // Open gallery
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ), GALLERY_PERMISSION_REQUEST_CODE
                        )
                    } else {
                        val intent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(intent, 1)
                    }
                }
                1 -> {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf(Manifest.permission.CAMERA),
                            CAMERA_PERMISSION_REQUEST_CODE
                        )
                    } else {
                        // Open camera
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, 0)
                    }
                }
            }
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    // Open camera
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 0)
                } else {
                    // Permission denied
                    Toast.makeText(
                        requireContext(),
                        "Please accept the camera permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            GALLERY_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, 1)
                } else {
                    // Permission denied
                    Toast.makeText(
                        requireContext(),
                        "Please accept the storage permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun uriToFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity?.contentResolver?.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()

        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = columnIndex?.let { cursor.getString(it) }
        cursor?.close()

        return filePath?.let { File(it) }
    }

    private fun saveBitmapToFile(bitmap: Bitmap): File {
        val file = File(context?.cacheDir, "captured-image.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }


    override fun onDestroyView() {
        updateProfileFlowJob?.cancel()
        super.onDestroyView()
    }

}