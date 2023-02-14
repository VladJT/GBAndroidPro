package jt.projects.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class TranslationDTO(
    @field:SerializedName("text")
    val translation: String?
)