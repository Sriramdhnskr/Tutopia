package com.example.tutopiaapplication.home.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentClassBinding
import com.example.tutopiaapplication.databinding.FragmentSubjectBinding
import com.example.tutopiaapplication.home.adapter.ProductsAdapter
import com.example.tutopiaapplication.model.ProductDetails
import com.example.tutopiaapplication.model.SubjectDetails
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.ItemClickListener

class ClassFragment : Fragment() , ItemClickListener {

    lateinit var binding : FragmentClassBinding

    lateinit var data : ArrayList<Any>

    lateinit var productsAdapter: ProductsAdapter

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClassBinding.inflate(inflater)

        val productDetails = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable<ProductDetails>(Constants.PRODUCT_DETAILS,ProductDetails::class.java)
        } else {
            arguments?.getParcelable<ProductDetails>(Constants.PRODUCT_DETAILS)
        }
        val subjectDetails = productDetails?.subjects
        Log.i("productDetails", productDetails.toString())


        binding.backImg.setOnClickListener{
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        setupData()

        if (productDetails != null) {
            binding.headerTxt.text = productDetails.className
        }

        binding.tilesRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.tilesRecyclerView.setHasFixedSize(true)
        binding.tilesRecyclerView.adapter = productsAdapter

        return binding.root
    }

    private fun setupData() {
        data = ArrayList<Any>()
        data.add(SubjectDetails("English"))
        data.add(SubjectDetails("Tamil"))
        data.add(SubjectDetails("Sanskrit"))
        data.add(SubjectDetails("Mathematics"))
        data.add(SubjectDetails("English"))
        data.add(SubjectDetails("Tamil"))
        data.add(SubjectDetails("Sanskrit"))
        data.add(SubjectDetails("Mathematics"))

        productsAdapter = ProductsAdapter(requireContext(), data,this)
    }

    override fun onButtonClicked(model: Any, pos: Int) {

        if(model is SubjectDetails)
        {
            bundle.putParcelable(Constants.SUBJECT_DETAILS,model)
            view?.findNavController()?.navigate(R.id.action_classFragment_to_subjectFragment,bundle)
        }
    }

}