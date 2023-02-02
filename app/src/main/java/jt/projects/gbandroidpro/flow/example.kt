package jt.projects.gbandroidpro.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


fun main() {
    println("СТАРТ")
    singleFlow(listOf<String>("TEN").asFlow())
    readln()
}

/**
 * Обратите внимание! Независимо от того, обрабатываем мы ошибку или нет, поток прервется и
вызовется оператор onCompletion
 */
private fun <T> collectFlow(flow: Flow<T>) {
    CoroutineScope(Dispatchers.Default).launch {
        flow
            .onCompletion { println("onCompletion") } // вызывается при завершении потока
            .catch { error ->
                println("Error: $error")
            } // вызывается при ошибке
            .collect {
                println("getFlow $it")
            }
    }
}

//Функция single() возвращает единственный элемент потока, если поток содержит только один элемент.
private fun <T : Any> singleFlow(flow: Flow<T>) {
    CoroutineScope(Dispatchers.Default).launch {
        val result = CoroutineScope(Dispatchers.Default).async {
            flow
                .onCompletion { println("onCompletion") }
                .single()
        }
        println("*** ${result.await()} ***")
    }
}


