package jt.projects.utils

const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"

const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "BOTTOM_SHEET_FRAGMENT_DIALOG_TAG"

const val NETWORK_STATUS_INTENT_FILTER = "NETWORK_STATUS"

/**
 * SHARED PREFS
 */
const val SP_DB_NAME = "settings"
const val SP_DB_KEY = "ALL_SETTINGS_IN_JSON_FORMAT"

/**
 * WIDGET
 */
const val INTENT_ACTION_WIDGET_CLICKED = "INTENT_ACTION_WIDGET_CLICKED"
const val INTENT_ACTION_WIDGET_UPDATE_DATA = "INTENT_ACTION_WIDGET_UPDATE_DATA"
const val WIDGET_DATA = "WIDGET_DATA"


/**
 * EXCEPTIONS
 */
val ViewModelNotInitException = IllegalStateException("The ViewModel should be initialised first")
val InteractorException = IllegalStateException("Something wrong in interactor")

/**
 * LOGS
 */
const val LOG_TAG = "TAG"

/**
 * DI
 */
const val NAME_REMOTE = "Remote"
const val NAME_LOCAL = "Local"
const val NETWORK_SERVICE = "NETWORK_SERVICE"

/**
 * BUILD CONFIG
 */
const val FAKE = "FAKE"
const val REAL = "REAL"
