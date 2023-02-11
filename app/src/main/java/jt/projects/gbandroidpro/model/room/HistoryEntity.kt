package jt.projects.gbandroidpro.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import jt.projects.model.data.DataModel
import jt.projects.model.data.Meanings
import jt.projects.model.data.Translation

// Так как мы пишем на Kotlin, то достаточно написать поля в конструкторе
// класса. В качестве основной ячейки мы используем ячейку "слово", то есть
// слово, которое мы искали и хотим сохранить. unique = true означает, что
// в БД не будут сохраняться повторяющиеся слова.
@Entity(indices = [Index(value = arrayOf("word"), unique = true)])
class HistoryEntity(
// Мы храним слово и его перевод
    @field:PrimaryKey
    @field:ColumnInfo(name = "word")
    var word: String,

    @field: ColumnInfo(name = "description")
    var description: String?,

    @field: ColumnInfo(name = "imageUrl")
    var imageUrl: String?,

    @field: ColumnInfo(name = "soundUrl")
    var soundUrl: String?,

    @field: ColumnInfo(name = "transcription")
    var transcription: String?,

    @field: ColumnInfo(name = "comment")
    var comment: String? = ""
)


fun HistoryEntity.toDataModel(): DataModel {
    return DataModel(
        this.word,
        meanings = listOf(
            Meanings(
                translation = Translation(this.description),
                imageUrl = this.imageUrl,
                soundUrl = this.soundUrl,
                transcription = this.transcription
            )
        )
    )
}
