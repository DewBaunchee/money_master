package by.varyvoda.android.moneymaster.data.converter

import androidx.room.TypeConverter
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount

class MoneyAmountConverters {

    @TypeConverter
    fun toString(moneyAmount: MoneyAmount) =
        "${moneyAmount.numerator}[${moneyAmount.denominatorPower}]"

    @TypeConverter
    fun toMoneyAmount(string: String) =
        MoneyAmount(
            numerator = string.substringBefore("[").toLong(),
            denominatorPower = string.substringAfter("[").substringBefore("]").toInt()
        )
}