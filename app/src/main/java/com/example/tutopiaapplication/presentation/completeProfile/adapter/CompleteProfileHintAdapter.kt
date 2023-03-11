package com.example.tutopiaapplication.presentation.completeProfile.adapter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.domain.model.SchoolMappedItem
import com.example.tutopiaapplication.utils.ItemClickListener
import com.example.tutopiaapplication.utils.Listener

class CompleteProfileHintAdapter(
    var mContext: Context,
    var layout: Int,
    var tv: Int,
    private val pincodeEditText: EditText?,
    var items: List<SchoolMappedItem>,
    private val listener: ItemClickListener? = null
) :
    ArrayAdapter<SchoolMappedItem>(mContext, layout, tv, items) {

    /*init {
        spinner.setSpinnerFocusable()
    }*/
    var pinCode : Boolean?=null

    fun setData(pinCode: Boolean){
        this.pinCode = pinCode
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {

        Log.i("DropDown", "dropdwon entry : ${items}")
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.spinner_view_item, parent, false)
        var spinnerTxt = view.findViewById<TextView>(R.id.spinnerTxt)

        if (position == 0) {
            spinnerTxt.gravity = Gravity.CENTER_HORIZONTAL
        }
        spinnerTxt.text = items[position].name
        return view
//        }
    }

    override fun getCount(): Int {
        return if (pinCode == true) {
            1 // show one item in the spinner if pincode field is empty
        } else {
            items.size // show school items in the spinner if pincode is not empty
        }
    }


    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getItemId(position: Int): Long {
        return items[position].id?.toLong() ?: -1
    }

    override fun getPosition(item: SchoolMappedItem?): Int {
        return items.indexOf(item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.hint_popup_item, parent, false)

        val textview = view.findViewById<TextView>(R.id.spinnerTxt) as TextView
        textview.setBackground(ContextCompat.getDrawable(mContext, R.drawable.spinner_bg))
        if (position == 0) {
            textview.setTextColor(ContextCompat.getColor(mContext, R.color.hint_color));
        } else {
            textview.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }

        textview.text = getItem(position)?.name

       /* if (pincodeEditText?.text?.isEmpty() == true && pinCode.isNullOrEmpty() == true) {
            Log.i(
                "AdapterToast",
                "pincode getview : ${pinCode.isNullOrEmpty() == true} and  ${pincodeEditText?.text.isNullOrEmpty() == true}"
            )
            view.setOnClickListener {
                listener?.onButtonClicked(getItem(position),position)
                Toast.makeText(context, "Pincode field is empty", Toast.LENGTH_SHORT).show()
            }
        }*/

        Log.i("Adapter","value : ${pinCode}")

//        if(pinCode==true) {
            view.setOnClickListener {
                listener?.onButtonClicked(getItem(position), position)
//            Toast.makeText(context, "Pincode field is empty", Toast.LENGTH_SHORT).show()
            }
//        }


//        textview.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.spinner_bg,0)
        /*if (position == 0) {
            textview.text = ""
            textview.hint = getItem(position).toString() //"Hint to be displayed"
        } else {
            textview.text = getItem(position).toString()
        }*/

        return view
    }

}