package com.example.tutopiaapplication.login

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentRegisterBinding
import com.example.tutopiaapplication.login.adapter.HintAdapter


class RegisterFragment : Fragment() {

    lateinit var spinnerAdapter : SpinnerAdapter

    lateinit var binding : FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentRegisterBinding.inflate(inflater)

       spinnerAdapter = HintAdapter(requireContext(),binding.boardSpinner, R.layout.hint_popup_item,R.id.spinnerTxt,
           arrayListOf("Select Board","item1","item2","item3")
       )

        binding.boardSpinner.adapter = spinnerAdapter

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
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })*/


        spinnerAdapter = HintAdapter(requireContext(),binding.boardSpinner, R.layout.hint_popup_item,R.id.spinnerTxt,
            arrayListOf("Select Classes","item1","item2","item3")
        )

        binding.classSpinner.adapter = spinnerAdapter

        return binding.root
    }
}