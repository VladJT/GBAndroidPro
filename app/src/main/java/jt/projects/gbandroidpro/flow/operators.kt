package jt.projects.gbandroidpro.flow

import kotlinx.coroutines.Dispatchers
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


/**
 * Обработка ошибок
 * !! Независимо от того, обрабатываем мы ошибку или нет, поток прервется и вызовется onCompletion
 */
fun getRange() =
    (1..5).asFlow().map {
        //выбрасывается ошибка, если значение == 3
        check(it != 3) { "Значение == $it" }//текст ошибки
        it * it
    }

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
А еще мы можем трансформировать Flow в LiveData и подписываться уже на нее:
val myFlow = flow { /* */ }
val myLiveData = flow.asLiveData()

 */