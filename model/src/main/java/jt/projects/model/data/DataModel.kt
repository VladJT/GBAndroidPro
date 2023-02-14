package jt.projects.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataModel(
    val text: String = "",
    val meanings: String = "",
    val imageUrl: String = "",
    val soundUrl: String = "",
    val transcription: String = ""
) : Parcelable
