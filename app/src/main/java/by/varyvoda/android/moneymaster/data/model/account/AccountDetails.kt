package by.varyvoda.android.moneymaster.data.model.account

import androidx.room.Embedded
import androidx.room.Relation
import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency

data class AccountDetails(
    @Embedded
    val account: Account,
    @Relation(
        parentColumn = "currencyCode",
        entityColumn = "code",
    )
    val currency: Currency,
    @Relation(
        parentColumn = "themeId",
        entityColumn = "id"
    )
    val theme: AccountTheme,
) {
    val id get() = account.id
}