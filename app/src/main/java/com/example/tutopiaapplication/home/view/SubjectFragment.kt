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
import com.example.tutopiaapplication.databinding.FragmentSubjectBinding
import com.example.tutopiaapplication.home.adapter.ProductsAdapter
import com.example.tutopiaapplication.model.ChapterDetails
import com.example.tutopiaapplication.model.ProductDetails
import com.example.tutopiaapplication.model.SubjectDetails
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.ItemClickListener

class SubjectFragment : Fragment() , ItemClickListener{

    lateinit var binding : FragmentSubjectBinding

    lateinit var data : ArrayList<Any>

    lateinit var productsAdapter: ProductsAdapter

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubjectBinding.inflate(inflater)

        val subjectDetails = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable<SubjectDetails>(Constants.SUBJECT_DETAILS,SubjectDetails::class.java)
        } else {
            arguments?.getParcelable<SubjectDetails>(Constants.SUBJECT_DETAILS)
        }
        val chapterDetails = subjectDetails?.subjectName
        Log.i("chapterDetails", chapterDetails.toString())


        binding.backImg.setOnClickListener{
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        setupData()

        if (subjectDetails != null) {
            binding.headerTxt.text = subjectDetails.subjectName
        }

        binding.tilesRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.tilesRecyclerView.setHasFixedSize(true)
        binding.tilesRecyclerView.adapter = productsAdapter

        return binding.root
    }

    private fun setupData() {
        data = ArrayList<Any>()
        data.add(ChapterDetails("Chapter 1"))
        data.add(ChapterDetails("Chapter 2"))
        data.add(ChapterDetails("Chapter 3"))
        data.add(ChapterDetails("Chapter 4"))
        data.add(ChapterDetails("Chapter 5"))
        data.add(ChapterDetails("Chapter 6"))
        data.add(ChapterDetails("Chapter 7"))
        data.add(ChapterDetails("Chapter 8"))

        productsAdapter = ProductsAdapter(requireContext(), data,this)
    }

    override fun onButtonClicked(model: Any, pos: Int) {
        if(model is ChapterDetails)
        {
            bundle.putParcelable(Constants.CHAPTER_DETAILS,model)
            view?.findNavController()?.navigate(R.id.action_subjectFragment_to_chapterFragment,bundle)
        }
    }

}