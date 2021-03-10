package kim.uno.simpleapp.ui.document

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import kim.uno.simpleapp.MainCoroutineRule
import kim.uno.simpleapp.data.DataRepository
import kim.uno.simpleapp.data.Result
import kim.uno.simpleapp.data.dto.Document
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class DocumentViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockDataRepository: DataRepository
    private lateinit var documentViewModel: DocumentViewModel

    private lateinit var document: Document

    @Before
    fun setUp() {
        val inputStream = javaClass.classLoader.getResourceAsStream("Document.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        document = Gson().fromJson(json, Document::class.java)
    }

    @Test
    fun `toggle favorite`() = runBlocking {
        Mockito.`when`(mockDataRepository.isFavorite(isbn = document.isbn))
            .thenReturn(Result.Success(true))

        Mockito.`when`(mockDataRepository.toggleFavorite(isbn = document.isbn))
            .thenReturn(Result.Success(true))

        documentViewModel = DocumentViewModel(mockDataRepository)
        documentViewModel.setup(document)
        documentViewModel.toggleFavorite()
        documentViewModel.favorite.observeForever { }

        Assert.assertEquals(documentViewModel.favorite.value, true)
    }

}
