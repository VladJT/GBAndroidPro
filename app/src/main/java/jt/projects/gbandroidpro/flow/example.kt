package jt.projects.gbandroidpro.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.single


fun main() {
    println("СТАРТ")

//    val t = testStateFlow()
//    collectFlow(t)// 1 observer
//    collectFlow(t)// 2 observer
//
//    Thread.sleep(50)
//    t.value = 10

    //  testException()

    val observable = FlowCallback()
    val flow = createCallbackFlow(observable)

    val scope = CoroutineScope(Dispatchers.IO)

    val job = scope.launch {
        flow.collect {
            println("from callback: $it")
        }
    }

    scope.launch {
        while (isActive) {
            delay(500)
            observable.invoke()
        }
    }

    Thread.sleep(2500)
    scope.coroutineContext.cancelChildren()



    //Thread.sleep(2500)
    //job.cancel()

    //  observable.invoke()


    println("*** PRESS ENTER ***")
    readln()
}

fun testException() {
    val corExHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob() + corExHandler)

    val job = scope.launch {
        launch(SupervisorJob()) {
            delay(2000)
            10 / 0
        }

        while (true) {
            delay(500)
            println("TICK 1")
        }
    }

    scope.launch {
        while (true) {
            delay(500)
            println("TICK 2")
        }
    }

//    val job = scope.launch {
//        launch {
//            while (true) {
//                delay(500)
//                println("TICK CHILD")
//            }
//        }
//        while (isActive) {
//            delay(500)
//            println("TICK $isActive")
//        }
//
//    }
//
//    Thread.sleep(2000)
//    // job.cancel()
//    scope.coroutineContext.cancel()

}

fun handleError(t: Throwable) {
    println("Error: $t")
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
        println("*** single: ${result.await()} ***")
    }
}


