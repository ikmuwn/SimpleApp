package kim.uno.simpleapp.data

import kim.uno.simpleapp.data.dto.Search
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {

    suspend fun searchBook(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Flow<Result<Search>>

    suspend fun isFavorite(id: String): Flow<Result<Boolean>>

    suspend fun toggleFavorite(id: String): Flow<Result<Boolean>>

    suspend fun clearFavorite(): Flow<Result<Boolean>>

}