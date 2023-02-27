package jt.projects.model.data

import kotlinx.coroutines.flow.flowOf

val APPSTATE_ERROR_EMPTY_DATA = AppState.Error(
    Throwable("Перевод не найден")
)

val TEST_RESPONSE_SUCCESS = flowOf(DataModel("success"))
