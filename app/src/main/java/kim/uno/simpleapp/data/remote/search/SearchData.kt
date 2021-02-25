package kim.uno.simpleapp.data.remote.search

import kim.uno.simpleapp.data.remote.Retrofit
import javax.inject.Inject

class SearchData @Inject constructor(private val retrofit: Retrofit) {

    private val service by lazy { retrofit.createService(SearchService::class.java) }

    suspend fun searchBook(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ) = retrofit.proceed {
        service.searchBook(query = query, sort = sort, page = page, size = size)
    }

}