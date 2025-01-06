package by.varyvoda.android.moneymaster.data.model.account.operation

import by.varyvoda.android.moneymaster.data.model.domain.Id

interface SingleAccountOperation : Operation {
    val accountId: Id
}