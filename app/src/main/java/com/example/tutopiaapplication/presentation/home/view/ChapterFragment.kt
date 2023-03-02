package com.example.tutopiaapplication.presentation.home.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentChapterBinding
import com.example.tutopiaapplication.databinding.TitleCardLayoutBinding
import com.example.tutopiaapplication.presentation.home.adapter.TutorialsAdapter
import com.example.tutopiaapplication.model.ChapterDetails
import com.example.tutopiaapplication.model.TutorialDetails
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.ItemClickListener

class ChapterFragment : Fragment() , ItemClickListener {

    lateinit var binding: FragmentChapterBinding

    private lateinit var titleLayoutBinding: TitleCardLayoutBinding

    lateinit var data: ArrayList<Any>

    lateinit var tutorialsAdapter: TutorialsAdapter

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChapterBinding.inflate(inflater)

        titleLayoutBinding = binding.title

        titleLayoutBinding.headerTxt.visibility = View.GONE

        titleLayoutBinding.backImg.setOnClickListener{
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        titleLayoutBinding.userImg.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_chapterFragment_to_profileFragment)
        }

        val chapterDetails = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable(Constants.CHAPTER_DETAILS,ChapterDetails::class.java)
        } else {
            arguments?.getParcelable<ChapterDetails>(Constants.CHAPTER_DETAILS)
        }

        val className =  arguments?.getString(Constants.PRODUCT_NAME)
        val subjectName =  arguments?.getString(Constants.SUBJECT_NAME)
        var chapterName = chapterDetails?.chapterName
        /*bundle.putString(Constants.PRODUCT_NAME,className)
        bundle.putString(Constants.SUBJECT_NAME,subjectName)
        bundle.putString(Constants.CHAPTER_NAME,chapterDetails?.chapterName)*/

        val tutorialDetails = chapterDetails?.tutorials
        Log.i("chapterDetails", chapterDetails.toString())

        setupData()

        binding.chapterNameTxt.text = "${chapterName} , 6 Tutorials"
        binding.classNameTxt.text = className
        binding.subjectNameTxt.text = subjectName

       /* if (chapterDetails != null) {
            binding.headerTxt.text = chapterDetails.chapterName
        }*/

        binding.tutorialRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.tutorialRecyclerView.setHasFixedSize(true)
        binding.tutorialRecyclerView.adapter = tutorialsAdapter

        return binding.root
    }

    private fun setupData() {
        data = ArrayList<Any>()
        data.add(TutorialDetails("Introduction","Introduction","05:00 Mins"))
        data.add(TutorialDetails("Stanza1","Stanza1","05:00 Mins"))
        data.add(TutorialDetails("Stanza2","Stanza2","05:00 Mins"))
        data.add(TutorialDetails("Stanza3","Stanza3","05:00 Mins"))
        data.add(TutorialDetails("Stanza4","Stanza4","05:00 Mins"))
        data.add(TutorialDetails("Stanza5","Stanza5","05:00 Mins"))

        tutorialsAdapter = TutorialsAdapter(requireContext(), data,this)
    }

    override fun onButtonClicked(model: Any, pos: Int) {
        if(model is TutorialDetails)
        {
            Toast.makeText(requireContext(),"Video played for ${model.tutorialTitle}", Toast.LENGTH_LONG).show()
        }
    }
}