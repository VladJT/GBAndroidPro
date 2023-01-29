package jt.projects.gbandroidpro.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

suspend fun main() {
    val job = GlobalScope.launch {
        method1()
    }
}

fun method1() {
    println("cool")
}
