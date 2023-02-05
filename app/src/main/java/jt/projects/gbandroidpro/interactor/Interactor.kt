package jt.projects.gbandroidpro.interactor

import jt.projects.gbandroidpro.model.domain.DataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow


// Ещё выше стоит интерактор. Здесь уже чистая бизнес-логика
interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): List<T>

    suspend fun Flow<T>.toList() : List<T> {
        val currentFlow = this
        val result: MutableList<T> = mutableListOf()
        val job = CoroutineScope(Dispatchers.IO).async {
            currentFlow.collect {
                result.add(it)
            }
            result
        }
        return job.await()
    }
}