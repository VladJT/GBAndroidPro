package jt.projects.gbandroidpro.interactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow


// интерактор (чистая бизнес-логика)
interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): Flow<T>
}