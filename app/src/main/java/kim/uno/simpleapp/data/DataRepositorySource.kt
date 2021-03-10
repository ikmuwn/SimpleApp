package kim.uno.simpleapp.data

import kim.uno.simpleapp.data.dto.Search

interface DataRepositorySource {

    suspend fun searchBook(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Result<Search>

    suspend fun isFavorite(id: String): Result<Boolean>
    suspend fun toggleFavorite(id: String): Result<Boolean>
    suspend fun clearFavorite(): Result<Boolean>

}