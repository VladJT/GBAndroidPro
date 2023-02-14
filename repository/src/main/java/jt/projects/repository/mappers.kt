package jt.projects.repository


import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.model.data.MeaningsDTO
import jt.projects.model.data.SearchResultDTO
import jt.projects.repository.room.HistoryEntity

fun List<MeaningsDTO>?.toOneString(): String {
    val sb = StringBuilder()
    this?.forEach {
        sb.append(it.translation?.translation.plus(", "))
    }
    return sb.dropLast(2).toString()
}

fun mapHistoryEntityToDataModel(data: List<HistoryEntity>): List<DataModel> {
    val dataModel = ArrayList<DataModel>()
    if (data.isNotEmpty()) {
        for (entity in data) {
            dataModel.add(entity.toDataModel())
        }
    }
    return dataModel
}

fun mapSearchResultToDataModel(data: List<SearchResultDTO>): List<DataModel> {
    val dataModel = ArrayList<DataModel>()
    if (data.isNotEmpty()) {
        for (entity in data) {
            dataModel.add(entity.toDataModel())
        }
    }
    return dataModel
}

fun SearchResultDTO.toDataModel(): DataModel {
    return DataModel(
        text = this.text ?: "",
        meanings = this.meanings.toOneString(),
        imageUrl = this.meanings?.get(0)?.imageUrl ?: "",
        soundUrl = this.meanings?.get(0)?.soundUrl ?: "",
        transcription = this.meanings?.get(0)?.transcription ?: ""
    )
}

fun HistoryEntity.toDataModel(): DataModel {
    return DataModel(
        text = this.word,
        meanings = this.description ?: "",
        imageUrl = this.imageUrl ?: "",
        soundUrl = this.soundUrl ?: "",
        transcription = this.transcription ?: ""
    )
}

fun DataModel.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        word = this.text,
        description = this.meanings,
        imageUrl = this.imageUrl,
        soundUrl = this.soundUrl,
        transcription = this.transcription,
        comment = null
    )
}

fun AppState.toDataModel(): DataModel? {
    return when (this) {
        is AppState.Success -> {
            val data = this.data?.get(0)
            if (data == null || data.text.isEmpty()) {
                null
            } else {
                DataModel(
                    text = data.text,
                    meanings = data.meanings,
                    imageUrl = data.imageUrl,
                    soundUrl = data.soundUrl,
                    transcription = data.transcription
                )
            }
        }
        else -> null
    }
}