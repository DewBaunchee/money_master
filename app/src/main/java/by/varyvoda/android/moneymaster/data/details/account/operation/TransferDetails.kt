package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.data.model.account.operation.Transfer

data class TransferDetails(
    val model: Transfer,
) : OperationDetails {
    override val id get() = model.id
    override val type get() = model.type
}