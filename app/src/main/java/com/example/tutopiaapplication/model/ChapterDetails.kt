package com.example.tutopiaapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ChapterDetails(val chapterName: String ,val tutorials : @RawValue List<TutorialDetails>? = null) :
    Parcelable