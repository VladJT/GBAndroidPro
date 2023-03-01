package jt.projects.model.data

import kotlinx.coroutines.flow.flowOf

val EMPTY_RESPONSE_EXCEPTION = Throwable("Перевод не найден")

val APPSTATE_ERROR_EMPTY_DATA = AppState.Error(
    EMPTY_RESPONSE_EXCEPTION
)

val APPSTATE_SUCCESS = AppState.Success(
    listOf(DataModel("success"))
)

val TEST_RESPONSE_SUCCESS = flowOf(DataModel("success"))


