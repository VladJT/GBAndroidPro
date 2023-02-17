package jt.projects.model.data

import com.google.gson.annotations.SerializedName

class SearchResultDTO(
    @field:SerializedName("text")
    val text: String?,
    @field:SerializedName("meanings")
    val meanings: List<MeaningsDTO>?
)