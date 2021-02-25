package kim.uno.simpleapp.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import kim.uno.simpleapp.MainCoroutineRule
import kim.uno.simpleapp.data.DataRepository
import kim.uno.simpleapp.data.Result
import kim.uno.simpleapp.data.dto.Search
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockDataRepository: DataRepository
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var search: Search

    @Before
    fun setUp() {
        val inputStream = javaClass.classLoader.getResourceAsStream("SearchResponse.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        search = Gson().fromJson(json, Search::class.java)
    }

    @Test
    fun search() = runBlocking {
        Mockito.`when`(
            mockDataRepository.searchBook(
                query = "test",
                sort = null,
                page = 1,
                size = 50
            )
        ).thenReturn(flow {
            emit(Result.Progress())
            emit(Result.Success(search))
        })

        Mockito.`when`(mockDataRepository.clearFavorite())
            .thenReturn(flow {
                emit(Result.Success(true))
            })

        searchViewModel = SearchViewModel(mockDataRepository)
        searchViewModel.searchBook(query = "test")
        Assert.assertEquals(searchViewModel.items, search.documents)
    }

}
