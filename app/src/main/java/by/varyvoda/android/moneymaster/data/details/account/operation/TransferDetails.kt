package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Transfer
import kotlinx.coroutines.flow.Flow

data class TransferDetails(
    val model: Transfer,
    val sourceAccount: Flow<AccountDetails>,
    val destinationAccount: Flow<AccountDetails>,
) : OperationDetails {
    override val id get() = model.id
    override val type get() = model.type
}