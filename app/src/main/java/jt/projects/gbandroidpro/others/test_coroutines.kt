package jt.projects.gbandroidpro.others

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Test(private val s: String) {
    fun show(): String {
        return "Показ: $s"
    }
}

suspend fun main() {

    val scope = CoroutineScope(Dispatchers.IO)

    println("Показ")
    scope.launch {
        repeat(100) {
            delay(500)
            println(it)
        }
    }

    Thread.sleep(2000)
    scope.coroutineContext.cancelChildren()

    scope.launch {
        repeat(100) {
            delay(500)
            println(it)
        }
    }

//    scope.launch {
//        println("start")
//        doSmth()
//        println("end!") // вывод результата после задержки
//    }

    println("Hello,") // пока сопрограмма проводит вычисления, основной поток продолжает свою работу
    readln()

}

suspend fun doSmth() = suspendCoroutine<Unit> {
    Executors.newSingleThreadExecutor().execute {
        Thread.sleep(500)
        println("...some job...")
        it.resume(Unit)
    }
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

class MyViewModel : CoroutineScope by CoroutineScope(Dispatchers.Main + SupervisorJob() +
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
