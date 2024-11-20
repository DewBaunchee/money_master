package by.varyvoda.android.moneymaster.data.account

import by.varyvoda.android.moneymaster.data.domain.Money

data class AccountBalanceEdit(
    override val account: Account,
    val newValue: Money,
    val oldValue: Money,
) : AccountMutation {
    override val type = AccountMutation.Type.INCOME
}