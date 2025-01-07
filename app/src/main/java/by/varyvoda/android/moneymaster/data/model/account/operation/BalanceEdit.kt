package by.varyvoda.android.moneymaster.data.model.account.operation

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import java.util.UUID

@Entity(tableName = "balance_edit")
data class BalanceEdit(
    @PrimaryKey
    override val id: UUID,
    override val date: PrimitiveDate,
    override val accountId: Id,
    val correctionValue: MoneyAmount,
    val oldValue: MoneyAmount,
) : Operation, SingleAccountOperation {

    @Ignore
    override val type = Operation.Type.BALANCE_EDIT

}