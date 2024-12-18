package by.varyvoda.android.moneymaster.data.model.account.operation

import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import java.util.UUID

// TODO Id generation
interface Operation {

    enum class Type {
        BALANCE_EDIT,
        INCOME,
        EXPENSE,
        TRANSFER;

        companion object {
            val DEFAULT = EXPENSE
        }
    }

    val id: UUID

    val date: PrimitiveDate

    val type: Type

}

