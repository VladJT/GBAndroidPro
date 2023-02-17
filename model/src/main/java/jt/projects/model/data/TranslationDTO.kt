package jt.projects.model.data

import com.google.gson.annotations.SerializedName

class TranslationDTO(
    @field:SerializedName("text")
    val translation: String?
)