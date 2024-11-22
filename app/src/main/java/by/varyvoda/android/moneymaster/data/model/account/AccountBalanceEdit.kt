package by.varyvoda.android.moneymaster.data.model.account

import by.varyvoda.android.moneymaster.data.domain.Money

data class AccountBalanceEdit(
    val newValue: Money,
    val oldValue: Money,
) : AccountMutation {
    override val type = AccountMutation.Type.INCOME

    override fun mutate(balance: Money): Money {
        return newValue
    }

    override fun undo(balance: Money): Money {
        return oldValue
    }
}