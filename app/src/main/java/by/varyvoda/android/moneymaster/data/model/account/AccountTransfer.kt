package by.varyvoda.android.moneymaster.data.model.account

import by.varyvoda.android.moneymaster.data.domain.Money

data class AccountTransfer(
    val from: Account,
    val to: Account,
    val amount: Money
) : AccountMutation {
    override val type = AccountMutation.Type.TRANSFER

    override fun mutate(balance: Money): Money {
        TODO("Not yet implemented")
    }

    override fun undo(balance: Money): Money {
        TODO("Not yet implemented")
    }
}
