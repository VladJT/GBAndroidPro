package jt.projects.gbandroidpro.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import java.util.concurrent.atomic.AtomicInteger

class Test(val s: String) {
    fun show(): String {
        return "From Test show: $s"
    }
}

suspend fun main() {

    GlobalScope.launch { // запуск новой сопрограммы в фоне
        delay(1000L) // неблокирующая задержка на 1 секунду
        println("World!") // вывод результата после задержки
    }

    println("Hello,") // пока сопрограмма проводит вычисления, основной поток продолжает свою работу
    Thread.sleep(2000L) // блокировка основного потока на 2 секунды, чтобы сопрограмма успела произвести вычисления

}

// Основной поток, вызывающий runBlocking, блокируется до завершения сопрограммы внутри runBlocking.
suspend fun main2() {
    //   runBlocking {
    //  GlobalScope.launch(Dispatchers.Default)
    //            (0..500_000).map{
//                launch {   println(it) }
//            }.joinAll()

//    CoroutineScope(SupervisorJob())
//        .launch {
//
//            val viewModel = MyViewModel()
//            var fragment = MyFragment(viewModel)
//
//
//            //    viewModel.bar().consumeEach { println(it) }
//            viewModel.foo().join()
//
//            println("--Finish coroutines--")
//        }.join()
//    println("--Finish--")

    //    }//runblocking

    val viewModel = MyViewModel()
    viewModel.incMultiply()
    println(viewModel.counter)



    (1..10000).map {
        GlobalScope.launch {
            actor.send(ActorMessage.Inc(1))
        }
    }.joinAll()
    GlobalScope.launch { actor.send(ActorMessage.Print()) }.join()
}

sealed class ActorMessage {
    class Inc(val incValue: Int) : ActorMessage()
    class Print() : ActorMessage()
}

class MyViewModel : CoroutineScope by CoroutineScope(SupervisorJob() +
        CoroutineExceptionHandler { context, throwable ->
            println("***CATCH  $throwable")
        }) {

    @Volatile
    var counter: AtomicInteger = AtomicInteger(0)

    private val lock: Any = Any()

    suspend fun incOne(): Job {
        val job = launch(Dispatchers.IO) {
            // synchronized(lock) {
            counter.addAndGet(1)
            // }
        }
        return job
    }

    suspend fun incMultiply() {
        (1..10000).map {
            incOne()
        }.joinAll()
    }

    private val handler = CoroutineExceptionHandler { context, throwable ->
        println("catch $throwable")
    }

    fun bar() = GlobalScope.produce<Int>(handler) {
        (0..10).map {
            send(it)
            delay(10)
        }
        throw Exception("🚩️my_exception in bar()")
    }

    fun foo() = launch(handler) {
        val job1 = launch {
            try {
                delay(100000)
            } catch (t: Exception) {
                println("***CATCH*** ${t.message}")
            }
        }
        val job2 = launch {
            throw Exception("✂️my_exception in foo()")
        }
    }

    fun onCleared() {
        println("ViewModel onCleared")
        coroutineContext.cancel()
    }
}

val actor = GlobalScope.actor<ActorMessage> {
    var counter = 0
    for (message in channel) {
        when (message) {
            is ActorMessage.Inc -> {
                counter += message.incValue
            }
            is ActorMessage.Print -> {
                println(counter)
            }
        }
    }
}


class MyFragment(viewModel: MyViewModel) : CoroutineScope by CoroutineScope(SupervisorJob()) {
    fun onStop() {
        println("MyFragment onStop")
        coroutineContext.cancelChildren()
    }

    fun onDestroyView() {
        println("MyFragment onDestroyView")
        coroutineContext.cancel()
    }
}
