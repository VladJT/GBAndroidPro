package jt.projects.gbandroidpro.model.domain

fun List<Meanings>?.toOneString(): String {
    val sb = StringBuilder()
    this?.forEach {
        sb.append(it.translation?.translation.plus(", "))
    }
    return sb.dropLast(2).toString()
}