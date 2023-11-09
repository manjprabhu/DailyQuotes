package com.mnj.dailyquotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnj.dailyquotes.model.datamodel.Quote
import com.mnj.dailyquotes.model.repository.QuotesRepository
import com.mnj.dailyquotes.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(private val repo: QuotesRepository) : ViewModel() {

    private val _quoteFlow: MutableStateFlow<Status<Quote>> = MutableStateFlow(Status.Loading())
    val quoteFlow: StateFlow<Status<Quote>> = _quoteFlow.asStateFlow()

    init {
        getRandomQuote()
    }

    private fun getRandomQuote() = viewModelScope.launch {
        _quoteFlow.value = Status.Loading()
        val response = repo.getRandomQuote()
        _quoteFlow.value = handleNetworkResponse(response)
    }

    private fun handleNetworkResponse(response: Response<List<Quote>>): Status<Quote> {
        if (response.isSuccessful) {
            val msg = response.body()!![0]
            return Status.Success(msg)
        }
        return Status.Error(response.message())
    }
}