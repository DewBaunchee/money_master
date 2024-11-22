package by.varyvoda.android.moneymaster.data.model.account

import by.varyvoda.android.moneymaster.data.domain.Money
import by.varyvoda.android.moneymaster.data.model.currency.Currency

data class Account(
    val id: Long = 0,
    val name: String,
    val currency: Currency,
    val initialBalance: Money,
    val mutations: List<AccountMutation> = listOf<AccountMutation>()
) {

    val balance by lazy {
        mutations.fold(initialBalance) { currentBalance, mutation ->
            mutation.mutate(currentBalance)
        }
    }

    override fun toString(): String {
        return "$name $currency $balance"
    }
}