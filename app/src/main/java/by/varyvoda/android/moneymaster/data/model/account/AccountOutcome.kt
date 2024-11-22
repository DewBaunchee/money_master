package by.varyvoda.android.moneymaster.data.model.account

import by.varyvoda.android.moneymaster.data.domain.Money
import by.varyvoda.android.moneymaster.data.domain.PrimitiveDate
import java.util.Date

data class AccountOutcome(
    val outcome: Money,
    val category: AccountMutationCategory,
    val date: PrimitiveDate,
    val description: String,
    val images: List<Int>
) : AccountMutation {
    override val type = AccountMutation.Type.OUTCOME

    override fun mutate(balance: Money): Money {
        return balance - outcome
    }

    override fun undo(balance: Money): Money {
        return balance + outcome
    }
}
