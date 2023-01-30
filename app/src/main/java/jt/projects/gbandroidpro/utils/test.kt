package jt.projects.gbandroidpro.utils

import kotlinx.coroutines.*

// Основной поток, вызывающий runBlocking, блокируется до завершения сопрограммы внутри runBlocking.
suspend fun main() {
   // runBlocking {
        with(CoroutineScope(SupervisorJob() ))
  //  GlobalScope.launch(Dispatchers.Default)
        {
//            (0..500_000).map{
//                launch {   println(it) }
//            }.joinAll()
            launch {
                while (isActive){
                    println("Polling...")
                    delay(10000)
                }
            }
        }


        val viewModel = MyViewModel()
        println("Finish")

  // }
}

class MyViewModel:CoroutineScope by CoroutineScope(SupervisorJob()){
    fun OnCleared(){
        coroutineContext.cancel()
    }
}
