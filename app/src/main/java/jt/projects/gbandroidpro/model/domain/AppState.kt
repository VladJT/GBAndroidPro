package jt.projects.gbandroidpro.model.domain

import jt.projects.gbandroidpro.model.room.HistoryEntity
import jt.projects.gbandroidpro.utils.toOneString

// три состояния, в которых может быть приложение
sealed class AppState {
    data class Success(val data: List<DataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
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