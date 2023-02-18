package com.example.tutopiaapplication.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.ActivityHomePageBinding
import com.google.android.material.navigation.NavigationView

class HomePageActivity : AppCompatActivity() {
       private lateinit var binding: ActivityHomePageBinding

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var navController: NavController

    private val listener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if(destination.label=="HomeScreenFragment")
            {
                binding.appBar.visibility = View.VISIBLE
            }
            else
            {
                binding.appBar.visibility = View.GONE
            }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomePageBinding.inflate(layoutInflater)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home_page)
                    as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeScreenFragment, R.id.profileFragment), drawerLayout)
        navView.setupWithNavController(navController)


        binding.menuIcon.setOnClickListener {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                               drawerLayout.openDrawer(GravityCompat.START)
                else
                    drawerLayout.closeDrawer(
                    GravityCompat.END
                )
        }


        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }
}