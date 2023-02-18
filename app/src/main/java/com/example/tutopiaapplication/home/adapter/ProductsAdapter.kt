package com.example.tutopiaapplication.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.SingleProductViewBinding
import com.example.tutopiaapplication.model.ChapterDetails
import com.example.tutopiaapplication.model.ProductDetails
import com.example.tutopiaapplication.model.SubjectDetails
import com.example.tutopiaapplication.model.TutorialDetails
import com.example.tutopiaapplication.utils.ItemClickListener

class ProductsAdapter(
    private val context: Context,
    private val productsList: ArrayList<Any>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ProductsAdapter.ItemViewHolder>() {

    var count = 0;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleProductViewBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.single_product_view, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(productsList[position], position, context, count,itemClickListener)
        if ((position + 1) % 2 == 0) {
            count++;
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    class ItemViewHolder(val binding: SingleProductViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            details: Any,
            position: Int,
            context: Context,
            count: Int,
            itemClickListener: ItemClickListener
        ) {

            if(details is ProductDetails)
            {
            binding.titleTxt.text = details.className
            binding.statusTxt.text = details.status
            }
            else if(details is SubjectDetails)
            {
                binding.titleTxt.text = details.subjectName
                binding.statusTxt.visibility = View.GONE
            }
            else if(details is ChapterDetails)
            {
                binding.titleTxt.text = details.chapterName
                binding.statusTxt.visibility = View.GONE
            }
            /*else if(details is TutorialDetails)
            {
                binding.titleTxt.text = details.tutorialName
                binding.statusTxt.visibility = View.GONE
            }*/

            Log.i("status", "position : ${position} and count : ${count}")

            binding.cardViewBg.setOnClickListener {
                itemClickListener.onButtonClicked(details,position)
            }

            if (count % 2 == 0) {
                if (position % 2 == 0) {
                    binding.cardViewBg.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.primary_light
                        )
                    )
                } else {
                    binding.cardViewBg.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.secondary_light
                        )
                    )
                }
            } else {
                if (position % 2 == 0) {
                    binding.cardViewBg.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.secondary_light
                        )
                    )
                } else {
                    binding.cardViewBg.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.primary_light
                        )
                    )
                }
            }
        }
    }
}