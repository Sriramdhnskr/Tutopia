package com.example.tutopiaapplication.data.api.model.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterRequestEntity(
    val board_id: String?,
    val classes: String?,
    val country_code: String?  = "+91",
    val mobile: String?,
    val name: String?,
    val pincode: String? = null,
    val referral_source: String? = null,
    val referred_by: String? = null
): Parcelable