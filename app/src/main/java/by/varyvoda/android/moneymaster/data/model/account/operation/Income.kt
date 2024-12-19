package by.varyvoda.android.moneymaster.data.model.account.operation

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import java.util.UUID

@Entity(tableName = "income")
data class Income(
    @PrimaryKey
    override val id: UUID,
    override val date: PrimitiveDate,
    val accountId: Id,
    val amount: MoneyAmount,
    val categoryId: Id,
    val description: String,
) : Operation {

    @Ignore
    override val type = Operation.Type.INCOME

}
