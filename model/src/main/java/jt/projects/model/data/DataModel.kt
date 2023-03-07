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


val testData = DataModel(
    "go",
    "бежать",
    "imageUrl",
    "https://vimbox-tts.skyeng.ru/api/v1/tts?text=beer+garden&lang=en&voice=male_2"
)