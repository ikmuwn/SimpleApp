package kim.uno.simpleapp.ui.search

import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kim.uno.simpleapp.data.DataRepository
import kim.uno.simpleapp.data.dto.Document
import kim.uno.simpleapp.util.Paging
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val paging = Paging { searchBook(query.value) }

    val items = ObservableArrayList<Document>()
    val query by lazy {
        MutableLiveData<String?>().also {
            viewModelScope.launch {
                it.asFlow()
                    .debounce(500L)
                    .collect {
                        if (it != _query) {
                            refresh()
                            _query = it
                        }
                    }
            }
        }
    }

    private var _query: String? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun searchBook(query: String?) {
        if (query.isNullOrBlank()) {
            _progress.value = false
            return
        }

        viewModelScope.launch {
            _progress.value = true

            dataRepository.searchBook(query = query, sort = null, page = paging.page, size = 50)
                .success {
                    _error.value = false

                    if (paging.page == 1)
                        items.clear()

                    if (it.documents.isNotEmpty())
                        items.addAll(it.documents)

                    paging.success(it.meta.is_end)
                }.error { _, _ ->
                    _error.value = true
                    paging.error()
                }

            _progress.value = false
        }
    }

    fun loadMore() {
        paging.load()
    }

    fun refresh() {
        paging.refresh()
    }

    fun isFavorite(view: View, isbn: String) {
        viewModelScope.launch {
            val isFavorite = dataRepository.isFavorite(isbn).data
            view.isSelected = isFavorite == true
        }
    }

    fun documentDetail(view: View, item: Document, extras: FragmentNavigator.Extras) {
        val direction = SearchFragmentDirections.documentDetail(item)
        view.findNavController().navigate(direction, extras)
    }

}