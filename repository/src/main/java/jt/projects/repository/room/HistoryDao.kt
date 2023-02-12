package jt.projects.repository.room

import androidx.room.*

// ключевое слово suspend, которое намекает, что все
// запросы в БД будут асинхронными (корутины поддерживаются в Room изначально)

@Dao
interface HistoryDao {
    @Query("select * from HistoryEntity")
    suspend fun all(): List<HistoryEntity>

    // Получить конкретное слово
    @Query("select * from HistoryEntity where word like :word")
    suspend fun getDataByWord(word: String): HistoryEntity

    // Сохранить новое слово
    // OnConflictStrategy.IGNORE означает, что дубликаты не будут сохраняться
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)

    // Вставить список слов
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<HistoryEntity>)

    // Обновить слово
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(entity: HistoryEntity)

    // Удалить слово
    @Delete
    suspend fun delete(entity: HistoryEntity)

    // Удалить ВСЕ
    @Query("delete from HistoryEntity")
    suspend fun deleteAll()
}