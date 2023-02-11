package com.example.tutopiaapplication.login.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tutopiaapplication.login.LoginFragment
import com.example.tutopiaapplication.login.RegisterFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> {RegisterFragment()}
           1 -> {LoginFragment()}
           else -> {throw Resources.NotFoundException("position not found")}
       }
    }
}