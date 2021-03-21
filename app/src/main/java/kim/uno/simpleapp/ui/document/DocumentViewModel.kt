package kim.uno.simpleapp.ui.document

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kim.uno.simpleapp.data.DataRepository
import kim.uno.simpleapp.data.dto.Document
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    val item by lazy { MutableLiveData<Document>() }

    private val _favorite by lazy { MutableLiveData<Boolean>() }
    val favorite: LiveData<Boolean>
        get() = _favorite

    private val _progress by lazy { MutableLiveData<Boolean>() }
    val progress: LiveData<Boolean>
        get() = _progress

    fun setup(document: Document) {
        item.value = document
        viewModelScope.launch {
            _progress.value = true
            val isFavorite = dataRepository.isFavorite(item.value!!.isbn).data
            _favorite.value = isFavorite == true
            _progress.value = false
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _progress.value = true
            val isFavorite = dataRepository.toggleFavorite(item.value!!.isbn).data
            _favorite.value = isFavorite == true
            _progress.value = false
        }
    }

}