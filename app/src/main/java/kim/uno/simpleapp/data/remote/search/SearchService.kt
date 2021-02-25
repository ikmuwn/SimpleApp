package kim.uno.simpleapp.data.remote.search

import kim.uno.simpleapp.data.dto.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("v3/search/book")
    suspend fun searchBook(
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Response<Search>

}