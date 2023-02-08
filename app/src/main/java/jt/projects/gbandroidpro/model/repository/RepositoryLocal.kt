package jt.projects.gbandroidpro.model.repository

import jt.projects.gbandroidpro.model.domain.AppState


// Репозиторий представляет собой слой получения и хранения данных, которые он передаёт интерактору
interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDb(appState: AppState)
}