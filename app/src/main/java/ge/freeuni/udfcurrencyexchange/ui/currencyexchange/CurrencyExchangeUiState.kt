package ge.freeuni.udfcurrencyexchange.ui.currencyexchange

import ge.freeuni.udfcurrencyexchange.data.models.Currency

sealed interface CurrencyExchangeUiState {
    data object Loading : CurrencyExchangeUiState

    data class Content(
        val from: Currency,
        val fromValue: Double,
        val to: Currency,
        val toValue: Double,
    ) : CurrencyExchangeUiState

    data class Error(val message: String): CurrencyExchangeUiState
}