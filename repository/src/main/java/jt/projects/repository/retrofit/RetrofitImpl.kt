package jt.projects.repository.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import jt.projects.model.data.DataModel
import jt.projects.repository.datasource.DataSource
import jt.projects.repository.toDataModel
import jt.projects.utils.BASE_URL_LOCATIONS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl : DataSource<Flow<DataModel>> {

    // Добавляем suspend и .await()
    override suspend fun getDataByWord(word: String): Flow<DataModel> {
        val response = getService(BaseInterceptor.interceptor).searchAsync(word).await()
        return response
            .asFlow()
            .map {
                it.toDataModel()
            }
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