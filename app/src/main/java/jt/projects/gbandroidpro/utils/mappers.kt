package jt.projects.gbandroidpro.utils

import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.domain.Meanings
import jt.projects.gbandroidpro.model.room.HistoryEntity
import jt.projects.gbandroidpro.model.room.toDataModel

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
