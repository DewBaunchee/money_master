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

    companion object {
        val USD = Currency(code = "USD", name = "American Dollar", symbol = "$")
        val BYN = Currency(code = "BYN", name = "Belarusian Rouble", symbol = "BYN")
        val EUR = Currency(code = "EUR", name = "Euro", symbol = "€")
        val Default = USD
    }

    override fun toString(): String {
        return symbol
    }
}