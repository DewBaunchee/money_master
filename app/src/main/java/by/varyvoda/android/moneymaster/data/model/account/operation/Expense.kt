package by.varyvoda.android.moneymaster.data.model.account.operation

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import java.util.UUID

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey
    override val id: UUID,
    override val accountId: Id,
    override val date: PrimitiveDate,
    val amount: Money,
    val categoryId: Id,
    val description: String,
) : Operation {

    @Ignore
    override val type = Operation.Type.EXPENSE

    override fun mutate(balance: Money): Money {
        return balance - amount
    }

    override fun undo(balance: Money): Money {
        return balance + amount
    }
}
