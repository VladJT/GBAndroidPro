package jt.projects.gbandroidpro.utils


import jt.projects.gbandroidpro.model.room.HistoryEntity
import jt.projects.gbandroidpro.model.room.toDataModel
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.model.data.Meanings

fun List<Meanings>?.toOneString(): String {
    val sb = StringBuilder()
    this?.forEach {
        sb.append(it.translation?.translation.plus(", "))
    }
    return sb.dropLast(2).toString()
}

// Принимаем на вход список слов в виде таблицы из БД и переводим его в List<SearchResult>
fun mapHistoryEntityToSearchResult(data: List<HistoryEntity>): List<DataModel> {
    val dataModel = ArrayList<DataModel>()
    if (data.isNotEmpty()) {
        for (entity in data) {
            dataModel.add(entity.toDataModel())
        }
    }
    return dataModel
}

fun AppState.toHistoryEntity(): HistoryEntity? {
    return when (this) {
        is AppState.Success -> {
            val data = this.data
            if (data.isNullOrEmpty() || data[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(
                    word = data[0].text!!,
                    description = data[0].meanings.toOneString(),
                    imageUrl = data[0].meanings?.get(0)?.imageUrl,
                    soundUrl = data[0].meanings?.get(0)?.soundUrl,
                    transcription = data[0].meanings?.get(0)?.transcription
                )
            }
        }
        else -> null
    }
}
