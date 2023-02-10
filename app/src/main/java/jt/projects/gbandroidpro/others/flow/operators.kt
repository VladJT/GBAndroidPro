package jt.projects.gbandroidpro.others.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


/** ZIP
 * Выполнение программы прекратится, как только данные закончатся хотя бы в одном потоке. То есть,
если в одном потоке будет 3 строки, а в другом — 5, программа завершится объединением 3-х строк.
 */
var flowOne = flowOf("Юрий", "Александр", "Иван").flowOn(Dispatchers.Default)
var flowTwo = flowOf("Гагарин", "Пушкин", "Грозный").flowOn(Dispatchers.Default)

fun zipFlow() = flowOne.zip(flowTwo) { firstString, secondString ->
    "$firstString $secondString"
}

val flow1 = flow {
    var inc = 100
    while (true) {
        delay(100)
        emit(inc++)
    }
}

val flow2 = flow {
    var inc = 50
    while (true) {
        delay(100)
        emit(inc++)
    }
}

// объединяет потоки
fun mergeFlow() =
    merge(flow1, flow2)

// совмещает значения потоков
fun combineFlow() = combine(flow1, flow2) { flow1, flow2 -> flow1 + flow2 }


/**
 * Обработка ошибок
 * !! Независимо от того, обрабатываем мы ошибку или нет, поток прервется и вызовется onCompletion
 */
fun getRange() =
    (1..10)
        .asFlow()
        //.filter { it % 2 == 0 }
        .distinctUntilChanged { old, new -> true } // повторяющиеся значения - отбрасываются
        .withIndex()// индексирует элементы
        .debounce(500)// если не прошло ? ms - Данные "отскакивают"
        .onEach {
            delay(500)
            //выбрасывается ошибка, если значение == 3
            // check(it.value != 8) { "Значение == $it" }//текст ошибки

        }

//.sample(1000)//ограничивает интервал опроса изменения данных (некоторые значения выпадут!!)

/**
Задача терминальных операторов — собирать данные от источника. Они же запускают поток. Если в
цепочке операторов нет терминального, то поток не запустится. Терминальный оператор —
последний, так что добавить после него какие-либо еще у вас не получится.
Помимо collect есть single, toList и многие другие
Подписываться на потоки мы можем так:
val myFlow = { /* */ }
myFlow.collect { /* */ }
myFlow.single { /* */ }
myFlow.toList { /* */ }
myFlow.count() - количество полученных элементов


А еще мы можем трансформировать Flow в LiveData и подписываться уже на нее:
val myFlow = flow { /* */ }
val myLiveData = flow.asLiveData()

 */
