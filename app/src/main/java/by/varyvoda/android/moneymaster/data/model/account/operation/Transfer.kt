package by.varyvoda.android.moneymaster.data.model.account.operation

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import java.util.UUID

@Entity(tableName = "transfer")
data class Transfer(
    @PrimaryKey
    override val id: UUID,
    override val date: PrimitiveDate,
    val sourceAccountId: Id,
    val destinationAccountId: Id,
    val sentAmount: Money,
    val receivedAmount: Money,
    val description: String,
) : Operation {

    @Ignore
    override val type = Operation.Type.TRANSFER

}
