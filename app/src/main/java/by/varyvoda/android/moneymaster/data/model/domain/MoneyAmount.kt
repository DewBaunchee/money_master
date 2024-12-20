package by.varyvoda.android.moneymaster.data.model.domain

import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount.Companion.FRACTION_DELIMITER
import kotlin.math.absoluteValue
import kotlin.math.pow

data class MoneyAmount(
    val numerator: Long,
    val denominatorPower: Int = 0,
) {

    companion object {
        const val FRACTION_DELIMITER = "."
    }

    // TODO Investigate lazy calculation
    val denominator get() = denominatorPower.toDenominator()

    val integer = numerator / denominator
    val fractional = numerator % denominator

    val doubleValue get() = numerator.toDouble() / denominator

    fun isNegative() = numerator < 0

    operator fun plus(amount: MoneyAmount): MoneyAmount {
        if (denominatorPower == amount.denominatorPower)
            return copy(numerator = numerator + amount.numerator)

        val bigger: MoneyAmount
        val lesser: MoneyAmount
        if (denominatorPower < amount.denominator) {
            bigger = this
            lesser = amount
        } else {
            lesser = this
            bigger = amount
        }

        return MoneyAmount(
            bigger.numerator
                    * (lesser.denominatorPower - bigger.denominatorPower).toDenominator()
                    + lesser.numerator,
            denominatorPower = lesser.denominatorPower,
        )
    }

    operator fun unaryMinus(): MoneyAmount = copy(numerator = -numerator)

    operator fun minus(amount: MoneyAmount) = this + -amount

    operator fun times(amount: MoneyAmount) =
        MoneyAmount(
            numerator * amount.numerator,
            denominatorPower + amount.denominatorPower,
        )

    fun toString(
        fractionDelimiter: String = FRACTION_DELIMITER,
        absolute: Boolean = false
    ): String =
        "${if (absolute) integer.absoluteValue else integer}" +
                if (fractional != 0L) "$fractionDelimiter$fractional" else ""

    override fun toString(): String = toString(absolute = false)

    private fun Int.toDenominator() = 10.0.pow(this).toLong()
}

fun Int.toMoneyAmount(): MoneyAmount = toLong().toMoneyAmount()

fun Long.toMoneyAmount(): MoneyAmount = MoneyAmount(numerator = this)

fun Double.toMoneyAmount(): MoneyAmount = toString().toMoneyAmountOrNull()!!

fun String.toMoneyAmountOrNull(): MoneyAmount? {
    val integer = substringBefore(FRACTION_DELIMITER).toLongOrNull() ?: return null
    val fractionString = substringAfter(FRACTION_DELIMITER, missingDelimiterValue = "")
    val fraction =
        if (fractionString.isBlank()) 0
        else fractionString.toLongOrNull() ?: return null

    val denominatorPower = fractionString.length

    return MoneyAmount(
        numerator = integer * 10.0.pow(denominatorPower).toLong()
                + if (integer < 0) -fraction else fraction,
        denominatorPower = denominatorPower,
    )
}