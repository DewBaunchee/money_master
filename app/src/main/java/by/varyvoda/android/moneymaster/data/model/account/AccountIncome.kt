package by.varyvoda.android.moneymaster.data.model.account

import by.varyvoda.android.moneymaster.data.domain.Money
import by.varyvoda.android.moneymaster.data.domain.PrimitiveDate

data class AccountIncome(
    val income: Money,
    val category: AccountMutationCategory,
    val date: PrimitiveDate,
    val description: String,
    val images: List<Int>
) : AccountMutation {
    override val type = AccountMutation.Type.INCOME

    override fun mutate(balance: Money): Money {
        return balance + income
    }

    override fun undo(balance: Money): Money {
        return balance - income
    }
}
