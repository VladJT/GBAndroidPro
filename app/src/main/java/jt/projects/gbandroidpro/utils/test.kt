package jt.projects.gbandroidpro.utils

import kotlinx.coroutines.*

// Основной поток, вызывающий runBlocking, блокируется до завершения сопрограммы внутри runBlocking.
suspend fun main() {
    // runBlocking {
    //  GlobalScope.launch(Dispatchers.Default)
    //            (0..500_000).map{
//                launch {   println(it) }
//            }.joinAll()

    with(CoroutineScope(SupervisorJob()))
    {

        launch {
            while (isActive) {
                println("Polling...")
                delay(10000)
            }
        }


        val viewModel = MyViewModel()
        var fragment = MyFragment(viewModel)
        println("Finish")

        viewModel.foo()

        val jobs = (0..3).map { viewModel.bar() }

        fragment.onStop()
        fragment.onDestroyView()

        fragment = MyFragment(viewModel)

        jobs.joinAll()

        fragment.onStop()
        fragment.onDestroyView()

        viewModel.onCleared()

        cancel()
    }

    // }
}

class MyViewModel : CoroutineScope by CoroutineScope(SupervisorJob()) {
    fun bar() = launch {
        delay(500)
        println("Barring")
    }

    fun foo() = launch {
        while (isActive) {
            println("fooing...")
            delay(5000)
        }
    }

    fun onCleared() {
        println("ViewModel onCleared")
        coroutineContext.cancel()
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
