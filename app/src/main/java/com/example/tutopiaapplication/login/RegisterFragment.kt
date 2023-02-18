package com.example.tutopiaapplication.login

import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentRegisterBinding
import com.example.tutopiaapplication.login.adapter.HintAdapter
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.Listener


class RegisterFragment() : Fragment(){

    lateinit var spinnerAdapter : SpinnerAdapter

    lateinit var binding : FragmentRegisterBinding

    lateinit var listener: Listener

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentRegisterBinding.inflate(inflater)

       spinnerAdapter = HintAdapter(requireContext(),binding.boardSpinner, R.layout.hint_popup_item,R.id.spinnerTxt,
           arrayListOf("School Name","item1","item2","item3")
       )

        binding.boardSpinner.adapter = spinnerAdapter

        binding.boardSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

/*
                if (parent != null && position==0) {
                    (parent.getChildAt(0) as TextView).setTextColor(
                        Color.BLUE
                    )
                }*/
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

        })


        spinnerAdapter = HintAdapter(requireContext(),binding.boardSpinner, R.layout.hint_popup_item,R.id.spinnerTxt,
            arrayListOf("Select Classes","item1","item2","item3")
        )

        binding.classSpinner.adapter = spinnerAdapter

        binding.registerBtn.setOnClickListener{
            bundle.putString(Constants.FROM_FRAGMENT,"RegisterFragment")
            Navigation.findNavController(it).navigate(R.id.action_swipeLoginFragment_to_otpFragment,bundle)
        }

        binding.loginTxt.setOnClickListener {
            if(listener!=null){
                listener.onButtonClicked()
            }
        }

        return binding.root
    }



}