package by.varyvoda.android.moneymaster.data.account

import by.varyvoda.android.moneymaster.data.domain.Money
import java.time.LocalDate

data class AccountOutcome(
    override val account: Account,
    val outcome: Money,
    val category: AccountMutationCategory,
    val date: LocalDate,
    val description: String,
    val images: List<Int>
) : AccountMutation {
    override val type = AccountMutation.Type.OUTCOME
}
