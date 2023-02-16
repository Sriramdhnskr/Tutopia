package com.example.tutopiaapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ProductDetails(val className : String? ,val status : String?,val subjects:@RawValue List<SubjectDetails>? = null) : Parcelable