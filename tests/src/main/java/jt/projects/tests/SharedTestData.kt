package jt.projects.gbandroidpro

import jt.projects.model.data.DataModel

val expectedData: Map<String, DataModel> = mapOf(
    "go" to DataModel(
        "go",
        "идти, ходить, вести, проходить, становиться, проходить, умирать, исчезать, заканчиваться, (исправно) работать, сочетаться, говорить, издавать звук, помещаться, попытка"
    ),
    "gg" to DataModel("gg", "Хорошая игра"),
    "loaf" to DataModel("loaf", "буханка, буханка, бездельничать, бездельничать")
)

val expectedSize: Map<String, Int> = mapOf(
    "go" to 15,
    "gg" to 1,
    "loaf" to 8
)


fun DataModel.getMeaningsCount(): Int {
    if (this.meanings.isNullOrBlank()) return 0
    return this.meanings.split(',').count()
}