package by.varyvoda.android.moneymaster.data.model.account.operation

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import java.util.UUID

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey
    override val id: UUID,
    override val date: PrimitiveDate,
    override val accountId: Id,
    override val amount: MoneyAmount,
    override val categoryId: Id,
    override val description: String,
) : IncomeExpense {

    @Ignore
    override val type = Operation.Type.EXPENSE

}
