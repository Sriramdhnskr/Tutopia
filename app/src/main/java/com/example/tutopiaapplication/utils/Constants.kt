package com.example.tutopiaapplication.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val PRODUCT_DETAILS = "product details"
    const val PRODUCT_NAME = "product name"
    const val SUBJECT_DETAILS = "subject details"
    const val SUBJECT_NAME = "subject name"
    const val TUTORIAL_DETAILS = "tutorial details"
    const val CHAPTER_NAME = "chapter name"
    const val CHAPTER_DETAILS = "tutorial details"
    const val FROM_FRAGMENT = "from fragment"
    const val REGISTER_USERID = "user_id"
    const val REGISTER_OTP = "Otp"
    const val REGISTER_DETAILS = "register details"
    const val PROFILE_DETAILS = "profile details"
    const val PROFILE = "profile"
    const val DEVICE_TOKEN = "student_device_token_123123"
    const val PLATFORM = "android"
    const val USER_ACCESS_TOKEN = "access_token"

    val FIRST_LOGIN_COMPLETE = booleanPreferencesKey("first_login_complete")
    val USER_ID = stringPreferencesKey("user_id")
    val OTP = stringPreferencesKey("Otp")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val ACCOUNT_ID = stringPreferencesKey("account_id")
    val USERNAME = stringPreferencesKey("username")
}