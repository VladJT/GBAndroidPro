package jt.projects.gbandroidpro.interactor


// интерактор (чистая бизнес-логика)
interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}