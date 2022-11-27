package jt.projects.gbandroidpro.model.retrofit

import io.reactivex.Observable
import jt.projects.gbandroidpro.model.domain.DataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {
    @GET("words/search")
    fun search(@Query("search") word: String): Observable<List<DataModel>>
}