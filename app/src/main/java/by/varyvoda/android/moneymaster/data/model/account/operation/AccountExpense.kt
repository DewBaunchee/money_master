package by.varyvoda.android.moneymaster.data.model.account.operation

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate

@Entity(tableName = "account_expense")
data class AccountExpense(
    @PrimaryKey(autoGenerate = true)
    override val id: Id = 0,
    override val accountId: Id,
    override val date: PrimitiveDate,
    val amount: Money,
    val categoryId: Id,
    val description: String,
) : AccountOperation {

    @Ignore
    override val type = AccountOperation.Type.EXPENSE

    override fun mutate(balance: Money): Money {
        return balance - amount
    }

    override fun undo(balance: Money): Money {
        return balance + amount
    }
}
