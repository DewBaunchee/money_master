package by.varyvoda.android.moneymaster.data.model.account.operation

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate

@Entity(tableName = "transfer")
data class Transfer(
    @PrimaryKey(autoGenerate = true)
    override val id: Id,
    override val accountId: Id,
    override val date: PrimitiveDate,
    val relatedAccountId: Id,
    val amount: Money
) : Operation {

    @Ignore
    override val type = Operation.Type.TRANSFER

    override fun mutate(balance: Money): Money {
        return balance + amount
    }

    override fun undo(balance: Money): Money {
        return balance - amount
    }
}
