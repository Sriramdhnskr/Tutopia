package com.example.tutopiaapplication.presentation.auth.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.domain.model.MappedItem

class HintAdapter(
    var mContext: Context,
    var layout: Int,
    var tv: Int,
    var items: List<MappedItem>,
) :
    ArrayAdapter<MappedItem>(mContext, layout, tv, items) {

    /*init {
        spinner.setSpinnerFocusable()
    }*/

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view =
            LayoutInflater.from(mContext).inflate(R.layout.spinner_view_item, parent, false)
        var spinnerTxt = view.findViewById<TextView>(R.id.spinnerTxt)
      /*  if(position==0) {
            spinnerTxt.height = 0
            spinnerTxt.visibility = View.GONE
        }*/

        if(position==0) {
            spinnerTxt.gravity = Gravity.CENTER_HORIZONTAL
        }
            spinnerTxt.text = items[position].name

        return view
    }

    override fun isEnabled(position: Int): Boolean {
        return position!=0
    }

    override fun getItemId(position: Int): Long {
        return items[position].id?.toLong() ?: -1
    }

    override fun getPosition(item: MappedItem?): Int {
        return items.indexOf(item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.hint_popup_item, parent, false)

        val textview =  view.findViewById<TextView>(R.id.spinnerTxt) as TextView
        textview.setBackground(ContextCompat.getDrawable(mContext, R.drawable.spinner_bg))
        if (position == 0) {
            textview.setTextColor(ContextCompat.getColor(mContext, R.color.hint_color));
        } else {
            textview.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
//        textview.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.spinner_bg,0)
        /*if (position == 0) {
            textview.text = ""
            textview.hint = getItem(position).toString() //"Hint to be displayed"
        } else {
            textview.text = getItem(position).toString()
        }*/
        textview.text = getItem(position)?.name

        return view
    }

}