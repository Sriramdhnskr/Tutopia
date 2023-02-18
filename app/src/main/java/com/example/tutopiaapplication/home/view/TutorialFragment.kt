package com.example.tutopiaapplication.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentTutorialBinding
import com.example.tutopiaapplication.databinding.TutorialsItemLayoutBinding
import com.example.tutopiaapplication.home.adapter.ProductsAdapter
import com.example.tutopiaapplication.home.adapter.TutorialsAdapter
import com.example.tutopiaapplication.model.SubjectDetails
import com.example.tutopiaapplication.model.TutorialDetails
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.ItemClickListener

class TutorialFragment : Fragment() , ItemClickListener{

    lateinit var binding: FragmentTutorialBinding

    lateinit var data : ArrayList<Any>

    lateinit var tutorialsAdapter: TutorialsAdapter

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTutorialBinding.inflate(inflater)

//        binding.

//        setupData()

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
            Toast.makeText(requireContext(),"Video played for ${model.tutorialTitle}",Toast.LENGTH_LONG).show()
        }
    }

}