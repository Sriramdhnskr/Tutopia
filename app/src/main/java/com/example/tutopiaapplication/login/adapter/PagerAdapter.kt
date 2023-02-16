package com.example.tutopiaapplication.login.adapter

import android.content.res.Resources
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tutopiaapplication.login.LoginFragment
import com.example.tutopiaapplication.login.RegisterFragment
import com.example.tutopiaapplication.login.AuthFragment
import com.example.tutopiaapplication.utils.Listener

class PagerAdapter(fragment: AuthFragment) : FragmentStateAdapter(fragment) {

    var swipe = fragment

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val registerFragment = RegisterFragment()
        registerFragment.listener = object : Listener{
            override fun onButtonClicked() {
                Log.i("button click","I have called register frag lcikc")
                swipe.binding.viewPager.currentItem = 1
            }
        }

        val loginFragment = LoginFragment()
        loginFragment.listener = object : Listener{
            override fun onButtonClicked() {
                Log.i("button click","I have called register frag lcikc")
                swipe.binding.viewPager.currentItem = 0
            }
        }
       return when(position){
           0 -> {
              registerFragment
           }
           1 -> {loginFragment}
           else -> {throw Resources.NotFoundException("position not found")}
       }
    }
}