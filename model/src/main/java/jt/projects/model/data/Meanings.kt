package jt.projects.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Meanings(
    @field:SerializedName("translation")
    val translation: Translation?,
    @field:SerializedName("imageUrl")
    val imageUrl: String?,
    @field:SerializedName("soundUrl")
    val soundUrl: String? = "",
    @field:SerializedName("transcription")
    val transcription: String? = ""
) : Parcelable