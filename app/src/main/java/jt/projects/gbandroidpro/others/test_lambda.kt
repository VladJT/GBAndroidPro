package jt.projects.gbandroidpro.utils


fun main() {
    val inc: (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    val dec = { a: Int, b: Int -> a - b }

    math(inc, 3, 4, ::line2)
    math(dec, 10, 5, ::line2)


    val x = higherFun()
    val rez = x("a", "d")
    println(rez)

}

fun line2() {
    println("----------------")
}

fun math(x: (Int, Int) -> Int, a: Int, b: Int, e: () -> Unit) {
    println("$a $b = ${x(a, b)}")
    e.invoke()
}

/**
 * возврат функции
 */
fun makeString(s1: String, s2: String): String = "$s1 + $s2"

fun higherFun(): (String, String) -> String {
    return ::makeString
}