package jt.projects.model.data

import com.google.gson.annotations.SerializedName

class MeaningsDTO(
    @field:SerializedName("translation")
    val translation: TranslationDTO?,
    @field:SerializedName("imageUrl")
    val imageUrl: String?,
    @field:SerializedName("soundUrl")
    val soundUrl: String? = "",
    @field:SerializedName("transcription")
    val transcription: String? = ""
)