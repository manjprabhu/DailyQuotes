package com.mnj.dailyquotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnj.dailyquotes.db.QuoteEntity
import com.mnj.dailyquotes.model.repository.QuotesRepository
import com.mnj.dailyquotes.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(private val repo: QuotesRepository) : ViewModel() {

    private val _quoteFlow: MutableStateFlow<Status<QuoteEntity>> =
        MutableStateFlow(Status.Loading())
    val quoteFlow: StateFlow<Status<QuoteEntity>> = _quoteFlow.asStateFlow()

    private val _saveQuoteFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val saveQuoteFlow: SharedFlow<Boolean> = _saveQuoteFlow.asSharedFlow()

    init {
        getRandomQuote()
    }

    private fun getRandomQuote() = viewModelScope.launch {
        _quoteFlow.value = Status.Loading()
        val response = repo.getRandomQuote()
        _quoteFlow.value = handleNetworkResponse(response)
    }

    private fun handleNetworkResponse(response: Response<List<QuoteEntity>>): Status<QuoteEntity> {
        if (response.isSuccessful) {
            val msg = response.body()!![0]
            return Status.Success(msg)
        }
        return Status.Error(response.message())
    }

    fun saveQuote(quote: QuoteEntity) {
        viewModelScope.launch {
            repo.insert(quote)
            _saveQuoteFlow.emit(true)
        }
    }
}