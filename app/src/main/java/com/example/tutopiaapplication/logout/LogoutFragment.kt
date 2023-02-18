package com.example.tutopiaapplication.logout

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentLogoutBinding
import com.example.tutopiaapplication.utils.TwoButtonDialogListener

class LogoutFragment(var message: String = "Are you sure you want to signout?", val listener: TwoButtonDialogListener) : DialogFragment() {
    lateinit var binding: FragmentLogoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentLogoutBinding.inflate(inflater)

        binding.signoutTitleTxt.text = "TUTOPIA"
        binding.signoutdescTxt.text = "Do you want to logout ?"
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding.nobtn.setOnClickListener {
            listener.clickEvent(it,dialog)
        }
        binding.yesbtn.setOnClickListener {
            listener.clickEvent(it,dialog)
        }
//        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /* val dialog: Dialog? = dialog
         dialog?.window?.setDimAmount(0f)
//
         if (dialog != null) {
             val width = ViewGroup.LayoutParams.MATCH_PARENT
             val height = ViewGroup.LayoutParams.MATCH_PARENT
             dialog.window?.setLayout(width, height)
         }*/
    }
}