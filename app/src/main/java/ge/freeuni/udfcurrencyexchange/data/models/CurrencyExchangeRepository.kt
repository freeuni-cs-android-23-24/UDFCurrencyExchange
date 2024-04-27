package ge.freeuni.udfcurrencyexchange.data.models

import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class CurrencyExchangeRepository {

    suspend fun refresh(): Boolean {
        delay(4.seconds)
        return true
    }

    fun convert(from: Currency, to: Currency, fromValue: Double): Double {
       return Random.nextDouble(until = 100001)
    }
}

// TODO: Test data, replace with real data.
private val conversionRates = mapOf(
    Currency.USD to mapOf(Currency.USD to 1.0, Currency.GEL to 2.5, Currency.TRY to 18.0),
    Currency.GEL to mapOf(Currency.USD to 0.4, Currency.GEL to 1.0, Currency.TRY to 7.2),
    Currency.TRY to mapOf(Currency.USD to 0.055, Currency.GEL to 0.14, Currency.TRY to 1.0),
)