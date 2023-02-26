package jt.projects.gbandroidpro

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class CoffeeImpl : Coffee {
    private var name: String = ""

    override fun getCoffeeName(): String = name
}

interface Coffee {
    fun getCoffeeName(): String
}


class Example {

    @Test
    fun testGetMockedCoffeeName_Success() {
        //Создаем мок
        val coffee = Mockito.mock(Coffee::class.java)

        //"когда" вызовется getCoffeeName() "вернуть" Капучино
        `when`(coffee.getCoffeeName()).thenAnswer { "Капучино" }
        assertEquals(coffee.getCoffeeName(), "Капучино")
    }

    @Test
    fun testMultipleValues() {
        val list = listOf("first", "second")
        val iterator = Mockito.mock(Iterator::class.java)
        `when`(iterator.next()).thenReturn(0).thenReturn(1)

        assertEquals(list[0], list.get(iterator.next() as Int))
        assertEquals(list[1], list.get(iterator.next() as Int))
    }

    //Передаем соответствующий аргумент в метод compareTo и получаем нужное значение
    @Test
    fun testReturnValueDependentOnMethodParameter() {
        val MOCKITO = "mockito"
        val ESPRESSO = "espresso"
        val MOCKITO_INT = 1
        val ESPRESSO_INT = 2
        val comparable = mock(Comparable::class.java)
        //   `when`(comparable.compareTo(MOCKITO_INT)).thenReturn(MOCKITO_INT)
        //    `when`(comparable.compareTo(ESPRESSO)).thenReturn(ESPRESSO_INT)
        //    assertEquals(MOCKITO_INT, comparable.compareTo(MOCKITO))
        //    assertEquals(ESPRESSO_INT, comparable.compareTo(ESPRESSO))
    }

}