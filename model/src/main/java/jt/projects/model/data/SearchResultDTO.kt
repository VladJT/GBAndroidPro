package jt.projects.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class SearchResultDTO(
    @field:SerializedName("text")
    val text: String?,
    @field:SerializedName("meanings")
    val meanings: List<MeaningsDTO>?
)