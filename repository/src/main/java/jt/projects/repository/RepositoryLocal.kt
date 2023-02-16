package jt.projects.repository

import jt.projects.model.data.DataModel


// Репозиторий представляет собой слой получения и хранения данных, которые он передаёт интерактору
interface RepositoryLocal<T> : Repository<T> {
    suspend fun getAllData(): T
    suspend fun saveData(data: DataModel)
    suspend fun clearAllData()
}