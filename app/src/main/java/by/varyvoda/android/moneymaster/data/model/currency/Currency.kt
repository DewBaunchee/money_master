package by.varyvoda.android.moneymaster.data.model.currency

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey
    val code: String,
    val name: String,
    val symbol: String
) {

    override fun toString(): String {
        return symbol
    }
}