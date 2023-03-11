package com.example.tutopiaapplication.data.api.model.updateProfile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Profile(
    val active_class_id: String?,
    val address: @RawValue Any?,
    val admin_blocked: String?,
    val board_id: String?,
    val board_name: String?,
    val cafe_user: String?,
    val can_ask_question: String?,
    val cast_player: String?,
    val city_id: String?,
    val city_name: String?,
    val class_id: String?,
    val class_name: String?,
    val country_code: String?,
    val created_at: String?,
    val current_class_id: String?,
    val current_class_name: String?,
    val current_class_roman_letter: String?,
    val has_active_order: Int?,
    val has_confirmed_order: String?,
    val is_onboard_completed: String?,
    val is_profile_completed: String?,
    val is_subscribed: String?,
    val is_verified: String?,
    val mobile: String?,
    val name: String?,
    val otp:@RawValue Any?,
    val pincode: String?,
    val profile_image: @RawValue Any?,
    val qa_unread_count: String?,
    val ref_earn_code: String?,
    val referral_code: String?,
    val referred_by: String?,
    val role: String?,
    val roman_letter: String?,
    val school_code: String?,
    val school_name: String?,
    val show_old_dashboard: String?,
    val show_preparatory: String?,
    val subscribe_message: String?,
    val trial_mode: String?,
    val universal_link_code: String?,
    val unread_count: String?,
    val updated_at: String?,
    val user_id: String?,
    val whatsapp_number: @RawValue Any?
): Parcelable