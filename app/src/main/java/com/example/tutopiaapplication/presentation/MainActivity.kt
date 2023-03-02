package com.example.tutopiaapplication.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.tutopiaapplication.BuildConfig
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.ActivityMainBinding
import com.example.tutopiaapplication.presentation.logout.LogoutFragment
import com.example.tutopiaapplication.utils.TwoButtonDialogListener
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TwoButtonDialogListener{
    private lateinit var binding: ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeScreenFragment, R.id.profileFragment
        ), drawerLayout)
        navView.setupWithNavController(navController)

        binding.profileImg.setOnClickListener {
            navController.navigate(R.id.profileFragment)
        }

        navView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.logOut) {
                // your code
                binding.drawerLayout.closeDrawers()
                LogoutFragment(listener = this).show(supportFragmentManager,"LogoutFragment")
                return@setNavigationItemSelectedListener true
            }
            else if (item.itemId == R.id.profileFragment) {
                // your code
                binding.drawerLayout.closeDrawers()
                navController.navigate(R.id.profileFragment)
                return@setNavigationItemSelectedListener true
            }
            else if (item.itemId == R.id.homeScreenFragment) {
                // your code
                binding.drawerLayout.closeDrawers()
                return@setNavigationItemSelectedListener true
            }
            false
        }

        binding.menuIcon.setOnClickListener {
            // If the navigation drawer is not open then open it, if its already open then close it.
            if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.openDrawer(GravityCompat.START)
            else
                drawerLayout.closeDrawer(
                    GravityCompat.END
                )
        }

        val versionName = "V " + BuildConfig.VERSION_NAME
        binding.versionTile.versionnoTxt.text = versionName

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

    override fun clickEvent(view: View, dialog: Dialog?) {
        if(view.id== R.id.yesbtn) {
            navController.popBackStack(R.id.homeScreenFragment,true)
          navController
                .navigate(R.id.swipeLoginFragment)
        }
        dialog?.dismiss()
    }
}