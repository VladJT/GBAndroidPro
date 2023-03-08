package jt.projects.gbandroidpro


import jt.projects.gbandroidpro.others.Coffee
import jt.projects.gbandroidpro.others.ICoffee
import jt.projects.gbandroidpro.others.ICooker
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(
    MockitoExtension::class
)
class MockitoExample {
    @Mock
    lateinit var coffee: ICoffee

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        //   coffee.cooker = mock(Cooker::class.java)
    }

    @Test
    fun testGetMockedCoffeeName_Success() {
        `when`(coffee.getCoffeeName()).thenReturn("Капучино")
        assertEquals("Капучино", coffee.getCoffeeName())

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
    fun testVerify() {
        //Создаем мок
        //val coffee = mock(Coffee::class.java)
        `when`(coffee.getCode()).thenReturn(43)
        coffee.setCode(11)
        coffee.setCode(12)
        coffee.getCode()
        coffee.getCode()
        coffee.getCode()
        coffee.getCode()
        coffee.getCode()

        //Убеждаемся, что метод setId вызван, и туда передано значение именно 12
        verify(coffee).setCode(12)
        //Убеждаемся, что метод getId вызван два раза.
        verify(coffee, times(5)).getCode()
        //Использование других параметров
        verify(coffee, never()).getCoffeeName()
        verify(coffee, atLeastOnce()).getCode()
        verify(coffee, atLeast(2)).getCode()
        verify(coffee, times(2)).setCode(anyInt())
        verify(coffee, atMost(6)).getCode()
    }


    @Test
    fun testOrder() {
        //Создаем мок
        val mockCooker = mock(ICooker::class.java)
        val c = Coffee(mockCooker)

        c.makeCoffee()

        val inOrder = inOrder(mockCooker)
        verify(mockCooker, atLeastOnce()).boilWater()
        verify(mockCooker, times(1)).addCoffee()
        verify(mockCooker, times(1)).cook()

        inOrder.verify(mockCooker).boilWater()
        inOrder.verify(mockCooker).addCoffee()
        inOrder.verify(mockCooker).cook()
    }

    @Test
    fun testChainResult() {
        `when`(coffee.getCoffeeName()).thenReturn("Капучино").thenReturn("Moccka")
        assertEquals("Капучино", coffee.getCoffeeName())
        assertEquals("Moccka", coffee.getCoffeeName())
    }
}