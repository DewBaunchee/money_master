package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.data.model.account.operation.BalanceEdit

data class BalanceEditDetails(
    val model: BalanceEdit,
) : OperationDetails {
    override val id get() = model.id
    override val type get() = model.type
}