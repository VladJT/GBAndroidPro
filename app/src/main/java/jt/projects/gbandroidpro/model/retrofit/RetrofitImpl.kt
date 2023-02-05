package jt.projects.gbandroidpro.model.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import jt.projects.gbandroidpro.model.datasource.DataSource
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.utils.BASE_URL_LOCATIONS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Для корректной работы Ретрофита мы создадим два класса: BaseInterceptor и ApiService.
//BaseInterceptor вам должен быть знаком из предыдущих курсов. Благодаря ему мы можем выводить в
//логи запросы на сервер и его ответы, а также обрабатывать ошибки сервера.
// DictionaryApi — это имплементация запроса через Retrofit
class RetrofitImpl : DataSource<Flow<DataModel>> {

//    override fun getData(word: String): Observable<List<DataModel>> {
//        return getService(BaseInterceptor.interceptor).search(word)
//    }

    // Добавляем suspend и .await()
    override suspend fun getData(word: String): Flow<DataModel> {
        return getService(BaseInterceptor.interceptor).searchAsync(word).await().asFlow()
    }

    private fun getService(interceptor: Interceptor): DictionaryApi {
        return createRetrofit(interceptor).create(DictionaryApi::class.java)
    }

    // Обратите внимание на Builder: в addCallAdapterFactory теперь передаётся
    // CoroutineCallAdapterFactory() которая позволяет Retrofit работать с
    // корутинами. Для ее использования нужно прописать для Ретрофита зависимость
    // вместо той, которая была для Rx:
    // implementation 'com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2'
    private fun createRetrofit(interceptor: Interceptor): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create())
            //addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient(interceptor))
            .build()


    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }
}