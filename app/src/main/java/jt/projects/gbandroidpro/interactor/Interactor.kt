package jt.projects.gbandroidpro.interactor


// Ещё выше стоит интерактор. Здесь уже чистая бизнес-логика
interface Interactor<T> {
    // Use Сase: получение данных для вывода на экран
    // Используем RxJava
    // fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
    suspend fun getData(word: String, fromRemoteSource: Boolean): List<T>
}