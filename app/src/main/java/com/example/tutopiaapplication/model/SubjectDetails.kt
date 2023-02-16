package com.example.tutopiaapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class SubjectDetails(val subjectName : String ,val tutorials : @RawValue List<ChapterDetails>? = null) :
    Parcelable
