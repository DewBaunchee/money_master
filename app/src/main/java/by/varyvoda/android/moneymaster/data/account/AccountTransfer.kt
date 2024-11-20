package by.varyvoda.android.moneymaster.data.account

import by.varyvoda.android.moneymaster.data.domain.Money

data class AccountTransfer(
    override val account: Account,
    val relatedAccount: Account,
    val amount: Money
) : AccountMutation {
    override val type = AccountMutation.Type.TRANSFER
}
