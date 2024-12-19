package by.varyvoda.android.moneymaster.data.model.currency

import androidx.room.Entity
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount

@Entity(
    tableName = "currency_exchange_rate",
    primaryKeys = ["soldCurrencyCode", "boughtCurrencyCode"]
)
data class CurrencyExchangeRate(
    val soldCurrencyCode: String,
    val boughtCurrencyCode: String,
    val rate: MoneyAmount,
)