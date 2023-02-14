package jt.projects.gbandroidpro.others.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

suspend fun main() {
    //hotFlowExample()
    //coldFlowExample()
    combineTest()

    CoroutineScope(Dispatchers.IO).launch {
        val flow = flow {
            (1..10).forEach {
                emit(it)
            }
        }

        flow.map {
            delay(300)
            it + 1
        }
            .flowOn(Dispatchers.IO)//выполнять предыдущие опреаторы на данном контексте
            .collect(::println)


    }

    Thread.sleep(5000)
}


suspend fun hotFlowExample() {
    var producer: ReceiveChannel<Int>
    CoroutineScope(Dispatchers.IO).launch {
        producer = produce {
            (1..10).forEach {
                println("produce: $it")
                send(it)
            }
        }
        //consumeEach - после отработки канал закроется
        producer.consumeEach {
            println("get: $it")
        }
        // НЕ РАБОТАЕТ
//        producer.consumeEach {
//            println("consume №2 hot flow: $it")
//        }
    }
}

suspend fun coldFlowExample() {
    var flow: Flow<Int>
    var producer: ReceiveChannel<Int>
    CoroutineScope(Dispatchers.IO).launch {
        flow = flow {
            (1..10).forEach {
                println("emit: $it")
                emit(it)
            }
        }

        // любое число подписчиков
        flow.collect {
            println("first: $it")
        }

        flow.collect {
            println("second: $it")
        }
    }
}
