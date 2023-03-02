package com.example.tutopiaapplication.presentation.home.adapter

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
import com.example.tutopiaapplication.utils.ItemClickListener
import com.example.tutopiaapplication.utils.Listener

class ProductsAdapter(
    private val context: Context,
    private val productsList: ArrayList<Any>,
    private val itemClickListener: ItemClickListener,
    private val listener: Listener? = null
) : RecyclerView.Adapter<ProductsAdapter.ItemViewHolder>(){

    var count = 0;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleProductViewBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.single_product_view, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(productsList[position], position, context, count, itemClickListener,listener)
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
            itemClickListener: ItemClickListener,
            listener: Listener?
        ) {

            if (details is ProductDetails) {
                binding.titleTxt.text = details.className
                binding.statusTxt.text = details.status
                binding.tileImg.visibility = View.GONE
                binding.titleTxt.visibility = View.VISIBLE
            } else if (details is SubjectDetails) {
                binding.titleTxt.visibility = View.GONE
                binding.tileImg.visibility = View.VISIBLE
                binding.statusTxt.text = details.subjectName
            } else if (details is ChapterDetails) {
                binding.titleTxt.visibility = View.GONE
                binding.tileImg.visibility = View.VISIBLE
                binding.statusTxt.text = details.chapterName
            }
            /*else if(details is TutorialDetails)
            {
                binding.titleTxt.text = details.tutorialName
                binding.statusTxt.visibility = View.GONE
            }*/

            Log.i("status", "position : ${position} and count : ${count}")

            binding.cardViewBg.setOnClickListener {
                itemClickListener.onButtonClicked(details, position)
            }

            binding.statusTxt.setOnClickListener {
                if(details is ProductDetails && binding.statusTxt.text=="Activate")
                {
                    listener?.onButtonClicked(details.className)
                }
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