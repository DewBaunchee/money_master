package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import java.util.UUID

interface OperationDetails {

    val id: UUID

    val type: Operation.Type
}