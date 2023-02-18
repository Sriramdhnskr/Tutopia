package com.example.tutopiaapplication.utils

import android.app.Dialog
import android.view.View

interface TwoButtonDialogListener {
    fun clickEvent(view : View, dialog: Dialog?)
}