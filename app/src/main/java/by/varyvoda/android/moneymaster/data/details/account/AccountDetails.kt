package by.varyvoda.android.moneymaster.data.details.account

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import kotlinx.coroutines.flow.Flow

data class AccountDetails(
    val model: Account,
    val currency: Flow<Currency>,
) {
    val id get() = model.id
}