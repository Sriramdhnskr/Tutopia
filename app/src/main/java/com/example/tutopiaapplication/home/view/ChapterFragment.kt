package com.example.tutopiaapplication.home.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentChapterBinding
import com.example.tutopiaapplication.databinding.FragmentSubjectBinding
import com.example.tutopiaapplication.home.adapter.ProductsAdapter
import com.example.tutopiaapplication.model.ChapterDetails
import com.example.tutopiaapplication.model.SubjectDetails
import com.example.tutopiaapplication.model.TutorialDetails
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.ItemClickListener

class ChapterFragment : Fragment() , ItemClickListener {

    lateinit var binding: FragmentChapterBinding

    lateinit var data: ArrayList<Any>

    lateinit var productsAdapter: ProductsAdapter

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChapterBinding.inflate(inflater)

        val chapterDetails = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable(Constants.CHAPTER_DETAILS,ChapterDetails::class.java)
        } else {
            arguments?.getParcelable<ChapterDetails>(Constants.CHAPTER_DETAILS)
        }

        val tutorialDetails = chapterDetails?.tutorials
        Log.i("chapterDetails", chapterDetails.toString())

        setupData()

        binding.backImg.setOnClickListener{
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        if (chapterDetails != null) {
            binding.headerTxt.text = chapterDetails.chapterName
        }

        binding.tilesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.tilesRecyclerView.setHasFixedSize(true)
        binding.tilesRecyclerView.adapter = productsAdapter


        return binding.root
    }

    private fun setupData() {
        data = ArrayList<Any>()
        data.add(TutorialDetails("Tutorial 1"))
        data.add(TutorialDetails("Tutorial 2"))
        data.add(TutorialDetails("Tutorial 3"))
        data.add(TutorialDetails("Tutorial 4"))
        data.add(TutorialDetails("Tutorial 5"))
        data.add(TutorialDetails("Tutorial 6"))
        data.add(TutorialDetails("Tutorial 7"))
        data.add(TutorialDetails("Tutorial 8"))

        productsAdapter = ProductsAdapter(requireContext(), data, this)
    }

    override fun onButtonClicked(model: Any, pos: Int) {
        if (model is TutorialDetails) {
            bundle.putParcelable(Constants.TUTORIAL_DETAILS, model)
            view?.findNavController()
                ?.navigate(R.id.action_chapterFragment_to_tutorialFragment, bundle)
        }
    }
}