package by.varyvoda.android.moneymaster.ui.account.creation

import by.varyvoda.android.moneymaster.data.currency.Currency

data class AccountCreationUiState(
    val name: String = "",
    val currency: Currency? = null,
    val initialBalance: String = "",
)