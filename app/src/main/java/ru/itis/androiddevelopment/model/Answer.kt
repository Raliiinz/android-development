package ru.itis.androiddevelopment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(

    val text:String,
    var isChecked: Boolean=false

): Parcelable