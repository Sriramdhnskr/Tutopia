package com.example.tutopiaapplication.login

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.tutopiaapplication.databinding.FragmentSwipeLoginBinding
import com.example.tutopiaapplication.login.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class AuthFragment() : Fragment() /*, Listener*/{
    lateinit var binding: FragmentSwipeLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSwipeLoginBinding.inflate(inflater)

        val root: View = binding.tabLayout2.getChildAt(0)
        if (root is LinearLayout) {
            (root as LinearLayout).showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(
                  Color.parseColor("#E6E6DC"
            ))
            drawable.setSize(2, 1)
            (root as LinearLayout).dividerPadding = 30
            (root as LinearLayout).dividerDrawable = drawable
        }

        binding.viewPager.adapter = PagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.tabLayout2, binding.viewPager){ tab,index ->
            tab.text = when(index){
                0 -> {"REGISTER"}
                1 -> {"LOGIN"}
                else -> {throw Resources.NotFoundException("position not found")}
            }
        }.attach()

        return binding.root
    }

   /* override fun onButtonClicked(view: View?, binding: FragmentSwipeLoginBinding?) {
//        val currPos: Int = binding.viewPager.getCurrentItem()
        when(view?.id){
            com.example.tutopiaapplication.R.id.registerTxt ->{
                binding.viewPager.currentItem = 0
            }
            com.example.tutopiaapplication.R.id.loginTxt ->{
                binding.viewPager.currentItem = 1
            }
        }
    }*/
}