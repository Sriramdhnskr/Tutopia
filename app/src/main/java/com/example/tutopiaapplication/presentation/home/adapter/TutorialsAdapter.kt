package com.example.tutopiaapplication.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.TutorialsItemLayoutBinding
import com.example.tutopiaapplication.model.TutorialDetails
import com.example.tutopiaapplication.utils.ItemClickListener

class TutorialsAdapter (
    private val context: Context,
    private val tutorialsList: ArrayList<Any>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<TutorialsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: TutorialsItemLayoutBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.tutorials_item_layout, parent, false)

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(tutorialsList[position], position, context,itemClickListener)
    }

    override fun getItemCount(): Int {
        return tutorialsList.size
    }

    class ItemViewHolder(val binding: TutorialsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            details: Any,
            position: Int,
            context: Context,
            itemClickListener: ItemClickListener
        ) {
            if(details is TutorialDetails)
            {
                binding.titleTxt.text = details.tutorialTitle
                binding.descTxt.text = details.tutorialDescription
                binding.playTimeTxt.text = details.playTime
            }
            binding.root.setOnClickListener {
                itemClickListener.onButtonClicked(details,position)
            }
        }
        }
}