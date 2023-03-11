package com.example.tutopiaapplication.data.api.model.updateProfile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.File

@Parcelize
data class UpdateProfileFormRequest(
    val pinCode: String,
    val profileImage: File?=null,
    val password: String,
    val referralCode: String?=null,
    val confirmPassword : String,
    val schoolName: String?=null,
    val schoolCode: String?=null,
    ): Parcelable
