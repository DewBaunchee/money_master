package by.varyvoda.android.moneymaster.ui

import by.varyvoda.android.moneymaster.data.account.Account

data class AppUiState(
    val accounts: List<Account> = listOf<Account>()
)