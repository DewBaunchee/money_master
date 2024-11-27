package by.varyvoda.android.moneymaster.data.model.account.mutation

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate

@Entity(tableName = "account_balance_edit")
data class AccountBalanceEdit(
    @PrimaryKey(autoGenerate = true)
    override val id: Id = 0,
    override val accountId: Id,
    override val date: PrimitiveDate,
    val newValue: Money,
    val oldValue: Money,
) : AccountOperation {

    @Ignore
    override val type = AccountOperation.Type.BALANCE_EDIT

    override fun mutate(balance: Money): Money {
        return newValue
    }

    override fun undo(balance: Money): Money {
        return oldValue
    }
}