package kim.uno.simpleapp.ui.search

import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kim.uno.simpleapp.data.DataRepository
import kim.uno.simpleapp.data.Result
import kim.uno.simpleapp.data.dto.Document
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    val items by lazy {
        viewModelScope.launch {
            query.asFlow()
                .debounce(500L)
                .collect {
                    if (it != acceptQuery)
                        searchBook(query = it, page = 1)
                }
        }

        ObservableArrayList<Document>()
    }

    val query by lazy { MutableLiveData<String?>() }

    private var acceptQuery: String? = null
    private var requestPage = 0
    private var acceptPage = 0

    private val _progress by lazy { MutableLiveData<Boolean>() }
    val progress: LiveData<Boolean>
        get() = _progress

    private val _error by lazy { MutableLiveData<Boolean>() }
    val error: LiveData<Boolean>
        get() = _error

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun searchBook(query: String?, page: Int = acceptPage + 1) {
        if (query.isNullOrBlank()) {
            _progress.value = false
            items.clear()
            return
        } else if (query == acceptQuery)
            if (requestPage == page || (acceptPage == page && page != 1)) {
                _progress.value = false
                return
            }
        requestPage = page
        acceptQuery = query

        viewModelScope.launch {
            if (requestPage == 1) {
                items.clear()
                dataRepository.clearFavorite().collect { }
            }

            dataRepository.searchBook(query = query, sort = null, page = page, size = 50)
                .collect {
                    _progress.value = it is Result.Progress

                    if (it is Result.Success) {
                        val data = it.data!!
                        if (data.documents.isNotEmpty())
                            items.addAll(data.documents)

                        if (!data.meta.is_end) {
                            acceptPage = requestPage
                            requestPage = 0
                        }
                    } else if (it is Result.Error) {
                        _error.value = true
                        acceptQuery = null
                    }
                }
        }
    }

    fun loadMore() {
        searchBook(query = query.value)
    }

    fun refresh() {
        acceptPage = 0
        requestPage = 0
        searchBook(query = query.value)
    }

    fun isFavorite(view: View, isbn: String) {
        viewModelScope.launch {
            dataRepository.isFavorite(isbn).collect {
                if (it is Result.Success)
                    view.isSelected = it.data == true
            }
        }
    }
}