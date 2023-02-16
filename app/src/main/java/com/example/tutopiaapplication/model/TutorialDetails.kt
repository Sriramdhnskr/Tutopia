package com.example.tutopiaapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TutorialDetails(val tutorialName : String ,val video : String? = null): Parcelable
