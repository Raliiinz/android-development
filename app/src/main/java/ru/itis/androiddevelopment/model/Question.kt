package ru.itis.androiddevelopment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class Question(
    val id: Int,
    val text: String?,
    val answers:MutableList<Answer>
): Parcelable
