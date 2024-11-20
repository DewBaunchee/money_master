package by.varyvoda.android.moneymaster.data.account

import by.varyvoda.android.moneymaster.data.currency.Currency

class Account(
    val currency: Currency,
) {
    val mutations = mutableListOf<AccountMutation>()
}