package ge.freeuni.udfcurrencyexchange.ui.currencyexchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.freeuni.udfcurrencyexchange.data.models.Currency
import ge.freeuni.udfcurrencyexchange.data.models.CurrencyExchangeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrencyExchangeViewModel : ViewModel() {

    private val currencyExchangeRepository = CurrencyExchangeRepository()

    init {
        viewModelScope.launch {
            val isInitialized = currencyExchangeRepository.refresh()
            if (isInitialized) {
                _uiState.update { DEFAULT_CONTENT }
            } else {
                _uiState.update {
                    CurrencyExchangeUiState.Error(
                        message = "Could not retrieve Currency Rates!"
                    )
                }
            }
        }
    }

    private val _uiState = MutableStateFlow<CurrencyExchangeUiState>(
        CurrencyExchangeUiState.Loading
    )
    val uiState: StateFlow<CurrencyExchangeUiState> = _uiState

    fun onFromCurrencyChanged(newCurrency: Currency) {
        _uiState.update { prevState ->
            val content = (prevState as? CurrencyExchangeUiState.Content) ?: DEFAULT_CONTENT
            val toValue = currencyExchangeRepository.convert(
                from = newCurrency,
                fromValue = content.fromValue,
                to = content.to
            )
            content.copy(from = newCurrency, toValue = toValue)
        }
    }

    fun onToCurrencyChanged(newCurrency: Currency) {
        _uiState.update { prevState ->
            val content = (prevState as? CurrencyExchangeUiState.Content) ?: DEFAULT_CONTENT
            val fromValue = currencyExchangeRepository.convert(
                from = content.from,
                fromValue = content.fromValue,
                to = newCurrency
            )
            content.copy(to = newCurrency, toValue = fromValue)
        }
    }

    fun onFromValueChanged(value: Double) {
        _uiState.update { prevState ->
            val content = (prevState as? CurrencyExchangeUiState.Content) ?: DEFAULT_CONTENT
            val toValue = currencyExchangeRepository.convert(
                from = content.from,
                fromValue = value,
                to = content.to
            )
            content.copy(fromValue = value, toValue = toValue)
        }
    }

    fun onToValueChanged(value: Double) {
        _uiState.update { prevState ->
            val content = (prevState as? CurrencyExchangeUiState.Content) ?: DEFAULT_CONTENT
            val fromValue = currencyExchangeRepository.convert(
                from = content.to,
                fromValue = value,
                to = content.from
            )
            content.copy(fromValue = fromValue, toValue = value)
        }
    }

    fun onSwapClicked() {
        _uiState.update { prevState ->
            val content = (prevState as? CurrencyExchangeUiState.Content) ?: DEFAULT_CONTENT
            content.copy(
                fromValue = content.toValue,
                toValue = content.fromValue,
                to = content.from,
                from = content.to
            )
        }
    }

    companion object {
        private val DEFAULT_CONTENT = CurrencyExchangeUiState.Content(
            from = Currency.GEL,
            fromValue = 0.0,
            to = Currency.TRY,
            toValue = 0.0
        )
    }
}


