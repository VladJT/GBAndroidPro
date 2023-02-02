package jt.projects.gbandroidpro.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

fun getFlow(): Flow<Int> = flow {
    println("*** Start flow ***")
    (0..10).forEach {
        delay(500)
        println("emitting $it")
        emit(it) // испускаем новое число
    }
    println("*** END flow ***")
}.map {
    it * 2
}.flowOn(Dispatchers.Default)


//Функция flowOf() используется там, где нужно превратить данные во Flow:
fun test1() = flowOf(4, 2, 5, 1, 7).onEach {
    delay(500)
    println(it)
}.flowOn(Dispatchers.Default)

//Функция asFlow() помогает сконвертировать некоторые типы данных во Flow. К примеру:
fun test2() = (1..5).asFlow().onEach {
    delay(500)
    println(it)
}.flowOn(Dispatchers.Default)

