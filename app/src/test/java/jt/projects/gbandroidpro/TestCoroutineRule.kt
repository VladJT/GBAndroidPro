package jt.projects.gbandroidpro

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Данное Правило заставляет Корутины использовать TestCoroutineDispatcher — специальный
класс для тестирования кода, работающего на Корутинах. По аналогии с Правилом для тестирования
LiveData оно упрощает тестирование асинхронных задач и выполняет их сразу же и последовательно
 */
@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)


    override fun apply(base: Statement, description: Description?) = object :
        Statement() {

        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher)
            base.evaluate()
            Dispatchers.resetMain()
            testCoroutineScope.cleanupTestCoroutines()
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }

    fun <T> getResult(block: suspend () -> T): T {
        var result: T =
            runBlocking {

                withContext(testCoroutineScope.coroutineContext) { block.invoke() }
            }
        return result
    }

}