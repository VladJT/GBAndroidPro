package jt.projects.repository

import jt.projects.model.data.AppState


// Репозиторий представляет собой слой получения и хранения данных, которые он передаёт интерактору
interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDb(appState: AppState)
    suspend fun deleteAll()
}