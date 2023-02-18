package com.example.tutopiaapplication.utils

import android.view.View
import com.example.tutopiaapplication.databinding.FragmentSwipeLoginBinding
import com.example.tutopiaapplication.model.ProductDetails

interface Listener {
    fun onButtonClicked(details: String? = null)
}