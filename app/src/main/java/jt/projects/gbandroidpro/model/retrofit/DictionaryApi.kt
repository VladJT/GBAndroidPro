package jt.projects.gbandroidpro.model.retrofit

import jt.projects.gbandroidpro.model.domain.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataModel>>

// RX JAVA
//    @GET("words/search")
//    fun search(@Query("search") word: String): Observable<List<DataModel>>
}