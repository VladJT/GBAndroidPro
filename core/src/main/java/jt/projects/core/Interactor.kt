package jt.projects.core


// интерактор (чистая бизнес-логика)
interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}

interface LocalInteractor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
    suspend fun getAllData(): T
    suspend fun deleteAll()
}