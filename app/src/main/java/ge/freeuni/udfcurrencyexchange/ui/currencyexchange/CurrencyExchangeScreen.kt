package ge.freeuni.udfcurrencyexchange.ui.currencyexchange

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import ge.freeuni.udfcurrencyexchange.data.models.Currency

@Composable
fun CurrencyExchangeScreen(
    viewModel: CurrencyExchangeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is CurrencyExchangeUiState.Content -> CurrencyExchangeContent(
            content = state,
            onFromChanged = viewModel::onFromCurrencyChanged,
            onToChanged = viewModel::onToCurrencyChanged,
            onFromValueChanged = viewModel::onFromValueChanged,
            onToValueChanged = viewModel::onToValueChanged,
            onSwapClicked = viewModel::onSwapClicked
        )

        CurrencyExchangeUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        is CurrencyExchangeUiState.Error -> {
            Box(contentAlignment = Alignment.Center) {
                Text(text = state.message)
            }
        }
    }
}

@Composable
fun CurrencyExchangeContent(
    content: CurrencyExchangeUiState.Content,
    onFromChanged: (newCurrency: Currency) -> Unit,
    onFromValueChanged: (newValue: Double) -> Unit,
    onToChanged: (newCurrency: Currency) -> Unit,
    onToValueChanged: (newValue: Double) -> Unit,
    onSwapClicked: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row() {
            TextField(
                value = content.fromValue.toString(),
                onValueChange = { onFromValueChanged(it.toDouble()) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                value = content.toValue.toString(),
                onValueChange = { onToValueChanged(it.toDouble()) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}