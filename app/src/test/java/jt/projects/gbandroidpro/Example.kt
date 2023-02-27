package jt.projects.gbandroidpro


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension


class CoffeeImpl : Coffee {
    private var name: String = ""

    override fun getCoffeeName(): String = name
}

interface Coffee {
    fun getCoffeeName(): String
}

@ExtendWith(
    MockitoExtension::class
)
class Example {

    @Test
    fun testGetMockedCoffeeName_Success() {
        //Создаем мок
        val coffee = Mockito.mock(Coffee::class.java)

        //"когда" вызовется getCoffeeName() "вернуть" Капучино
        Mockito.`when`(coffee.getCoffeeName()).thenAnswer { "Капучино" }
        assertEquals(coffee.getCoffeeName(), "Капучино")
    }

    @Test
    fun testMultipleValues() {
        val list = listOf("first", "second")
        val iterator = Mockito.mock(Iterator::class.java)
        `when`(iterator.next()).thenReturn(0).thenReturn(1)

        assertEquals(list[0], list[iterator.next() as Int])
        assertEquals(list[1], list[iterator.next() as Int])
    }

    //Передаем соответствующий аргумент в метод compareTo и получаем нужное значение
    @Test
    fun testReturnValueDependentOnMethodParameter() {

    }

}