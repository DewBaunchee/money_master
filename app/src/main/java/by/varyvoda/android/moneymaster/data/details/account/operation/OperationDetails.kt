package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import java.util.UUID

interface OperationDetails {

    val id: UUID

    val type: Operation.Type

    val date: PrimitiveDate
}