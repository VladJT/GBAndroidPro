package jt.projects.gbandroidpro.model.domain

import jt.projects.gbandroidpro.model.room.HistoryEntity

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

fun HistoryEntity.toDataModel(): DataModel {
    return DataModel(
        this.word,
        meanings = listOf(
            Meanings(
                translation = Translation(
                    this.description ?: ""
                ), imageUrl = null
            )
        )
    )
}

fun AppState.toHistoryEntity(): HistoryEntity? {
    return when (this) {
        is AppState.Success -> {
            val searchResult = this.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(searchResult[0].text!!, searchResult[0].meanings.toOneString())
            }
        }
        else -> null
    }
}
