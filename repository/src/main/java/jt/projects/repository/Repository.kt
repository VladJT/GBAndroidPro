package jt.projects.repository


// Репозиторий представляет собой слой получения и хранения данных, которые он передаёт интерактору
interface Repository<T> {

    suspend fun getData(word: String): T
}