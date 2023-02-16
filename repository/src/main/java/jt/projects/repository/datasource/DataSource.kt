package jt.projects.repository.datasource


// Источник данных для репозитория (Интернет, БД и т. п.)
interface DataSource<T> {
    suspend fun getDataByWord(word: String): T
}