package jt.projects.gbandroidpro.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

// Помечаем класс как @Database и наследуемся от RoomDatabase. Передаём список
// таблиц entities = arrayOf(HistoryEntity::class), указываем версию БД (важно
// при миграции) и параметр exportSchema, который указывает, нужно ли хранить
// историю изменений в БД в отдельном файле (полезно при совместной
// разработке)

@Database(entities = [HistoryEntity::class], version = 5, exportSchema = true)
abstract class HistoryDatabase : RoomDatabase() {
    // Возвращаем DAO
    abstract fun historyDao(): HistoryDao
}