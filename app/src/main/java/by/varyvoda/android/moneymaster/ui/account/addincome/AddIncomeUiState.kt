package by.varyvoda.android.moneymaster.ui.account.addincome

import by.varyvoda.android.moneymaster.data.account.AccountMutationCategory
import by.varyvoda.android.moneymaster.data.domain.Id
import by.varyvoda.android.moneymaster.data.domain.PrimitiveDate

data class AddIncomeUiState(
    val accountId: Id? = null,
    val amount: String = "0",
    val date: PrimitiveDate? = null,
    val category: AccountMutationCategory? = null,
    val description: String = "",
    val images: List<Int> = listOf()
)