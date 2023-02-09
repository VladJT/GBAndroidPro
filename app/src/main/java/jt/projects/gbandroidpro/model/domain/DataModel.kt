package jt.projects.gbandroidpro.model.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataModel(
    @field:SerializedName("text")
    val text: String?,
    @field:SerializedName("meanings")
    val meanings: List<Meanings>?
) : Parcelable

@Parcelize
class Meanings(
    @field:SerializedName("translation")
    val translation: Translation?,
    @field:SerializedName("imageUrl")
    val imageUrl: String?,
    @field:SerializedName("soundUrl")
    val soundUrl: String?="",
    @field:SerializedName("transcription")
    val transcription: String?=""
) : Parcelable

@Parcelize
class Translation(
    @field:SerializedName("text")
    val translation: String?
) : Parcelable