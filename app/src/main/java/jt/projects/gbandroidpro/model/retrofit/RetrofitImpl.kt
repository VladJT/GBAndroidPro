package jt.projects.gbandroidpro.model.retrofit

import io.reactivex.rxjava3.core.Observable
import jt.projects.gbandroidpro.model.datasource.DataSource
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.utils.BASE_URL_LOCATIONS
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//Для корректной работы Ретрофита мы создадим два класса: BaseInterceptor и ApiService.
//BaseInterceptor вам должен быть знаком из предыдущих курсов. Благодаря ему мы можем выводить в
//логи запросы на сервер и его ответы, а также обрабатывать ошибки сервера.
// DictionaryApi — это имплементация запроса через Retrofit
class RetrofitImpl : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return getService(BaseInterceptor.interceptor).search(word)
    }

    private fun getService(interceptor: Interceptor): DictionaryApi {
        return createRetrofit(interceptor).create(DictionaryApi::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(createOkHttpClient(interceptor)).build()
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }
}