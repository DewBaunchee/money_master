package by.varyvoda.android.moneymaster.data.account

import by.varyvoda.android.moneymaster.data.domain.Money

interface AccountMutation {

    enum class Type {
        BALANCE_EDIT,
        INCOME,
        OUTCOME,
        TRANSFER,
    }

    val type: Type
    val account: Account
}

