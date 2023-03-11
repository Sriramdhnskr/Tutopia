package com.example.tutopiaapplication.presentation.home.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentHomeScreenBinding
import com.example.tutopiaapplication.presentation.home.adapter.ProductsAdapter
import com.example.tutopiaapplication.presentation.logout.LogoutFragment
import com.example.tutopiaapplication.model.ProductDetails
import com.example.tutopiaapplication.utils.*
import java.util.Calendar

class HomeScreenFragment : Fragment() , ItemClickListener , Listener , TwoButtonDialogListener{

    lateinit var binding : FragmentHomeScreenBinding

    lateinit var data : ArrayList<Any>

    lateinit var productsAdapter: ProductsAdapter

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater)

        val greeting = displayGreetingMessage()

        binding.greetingsTxt.text = greeting
        binding.nameTxt.text = "User"

        setupData()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val drawerLayout = activity!!.findViewById<DrawerLayout>(R.id.drawer_layout)

                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawers()
                }
                else{
                    findNavController().popBackStack()
                }
            }
        })

        binding.tilesRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.tilesRecyclerView.setHasFixedSize(true)
        binding.tilesRecyclerView.adapter = productsAdapter

        return binding.root
    }

    private fun displayGreetingMessage() : String{

        val cal = Calendar.getInstance()
        val timeOfDay = cal.get(Calendar.HOUR_OF_DAY)

        return when(timeOfDay)
        {
            in 0..11 -> "Good Morning!"
            in 12..15 -> "Good Afternoon!"
            in 16..20 -> "Good Evening!"
            in 21..23 -> "Good Night!"
            else -> "Hello"
        }


    }

    private fun setupData() {
        data = ArrayList<Any>()
        data.add(ProductDetails("Class 6","Activate"))
        data.add(ProductDetails("Class 7","Active"))
        data.add(ProductDetails("Class 8","Active"))
        data.add(ProductDetails("Class 9","Activate"))
        data.add(ProductDetails("Class 9","Activate"))
        data.add(ProductDetails("Class 9","Activate"))
        data.add(ProductDetails("Class 9","Activate"))
        data.add(ProductDetails("Class 9","Activate"))
        data.add(ProductDetails("Class 9","Activate"))
        data.add(ProductDetails("Class 9","Activate"))
        data.add(ProductDetails("Class 9","Activate"))
        data.add(ProductDetails("Class 9","Activate"))

        productsAdapter = ProductsAdapter(requireContext(), data,this,this)
    }

    override fun onButtonClicked(model: Any?, pos: Int) {
        if(model is ProductDetails)
        {
            bundle.putParcelable(Constants.PRODUCT_DETAILS,model)
            view?.findNavController()?.navigate(R.id.action_homeScreenFragment_to_classFragment,bundle)
        }
    }

    override fun onButtonClicked(details: String?) {
        val message = "Do You want to activate ${details}"
        LogoutFragment(message = message,listener = this).show(parentFragmentManager,"ActivateFragment")
    }

    override fun clickEvent(view: View, dialog: Dialog?) {
        dialog?.dismiss()
    }
}