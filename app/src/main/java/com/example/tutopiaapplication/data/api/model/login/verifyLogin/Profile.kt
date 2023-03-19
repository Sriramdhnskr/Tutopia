package com.example.tutopiaapplication.data.api.model.login.verifyLogin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Profile(
    val address: String,
    val admin_blocked: String,
    val can_place_order: Boolean,
    val city_id: String,
    val city_name: String,
    val class_id: String,
    val class_name: String,
    val country_code: String,
    val created_at: String,
    val is_subscribed: String,
    val mobile: String,
    val name: String,
    val order_status: String,
    val pincode: String,
    val pincode_id: String,
    val profile_image: @RawValue ProfileImage,
    val role: String,
    val roman_letter: String,
    val school_name: String,
    val show_preparatory: String,
    val unread_count: String,
    val updated_at: String,
    val user_id: String,
    val whatsapp_number: String
) : Parcelable