package jt.projects.gbandroidpro.model.datasource


// Источник данных для репозитория (Интернет, БД и т. п.)
interface DataSource<T> {
    //   fun getData(word: String): Observable<T>
    suspend fun getData(word: String): T
}