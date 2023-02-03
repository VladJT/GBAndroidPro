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
fun testFlowOf() = flowOf(4, 2, 5, 1, 7).onEach {
    delay(500)
    println(it)
}.flowOn(Dispatchers.Default)

//Функция asFlow() помогает сконвертировать некоторые типы данных во Flow. К примеру:
fun testAsFlow() = (1..5).asFlow().onEach {
    delay(500)
    println(it)
}.flowOn(Dispatchers.Default)

/**
 * StateFlow
 * Изменить же значение у StateFlow довольно просто: как только значение меняется через сеттер —
подписчик сразу об этом узнает и получает новое значение через collect

На StateFlow могут подписываться более одного пользователя;
● Мы можем получать значение StateFlow напрямую через геттер, не подписываясь на поток и
не ожидая, когда значение будет испускаться;
● Обновление значения StateFlow может происходить откуда угодно, где есть ссылка на
MutableStateFlow;
● StateFlow не привязан к CoroutineScope. То есть он не привязан к конкретным классам и может
выполняться откуда угодно;
● У StateFlow всегда есть начальное состояние, поэтому мы всегда можем получить какое-то
значение, даже если будет всего одна эмиссия;
● Запуск StateFlow можно инициировать, даже если на него никто не подписан (аналог hot
observable).

 */
fun testStateFlow() = MutableStateFlow(5)