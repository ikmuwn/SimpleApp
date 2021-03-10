package kim.uno.simpleapp.data

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kim.uno.simpleapp.data.local.LikeData
import kim.uno.simpleapp.data.remote.search.SearchData
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
open class DataRepository @Inject constructor(
    private val searchData: SearchData,
    private val likeData: LikeData
) : DataRepositorySource {

    override suspend fun searchBook(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ) = searchData.searchBook(query = query, sort = sort, page = page, size = size)

    override suspend fun isFavorite(isbn: String) = likeData.isFavorite(isbn)
    override suspend fun toggleFavorite(isbn: String) = likeData.toggleFavorite(isbn)
    override suspend fun clearFavorite() = likeData.clearFavorite()

}