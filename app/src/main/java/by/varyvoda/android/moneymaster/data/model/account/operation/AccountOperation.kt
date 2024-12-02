package by.varyvoda.android.moneymaster.data.model.account.operation

import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate

interface AccountOperation {

    enum class Type {
        BALANCE_EDIT,
        INCOME,
        EXPENSE,
        TRANSFER,
    }

    val id: Id

    val accountId: Id

    val date: PrimitiveDate

    val type: Type

    fun mutate(balance: Money): Money

    fun undo(balance: Money): Money
}

