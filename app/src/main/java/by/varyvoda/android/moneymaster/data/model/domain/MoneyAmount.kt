package by.varyvoda.android.moneymaster.data.model.domain

import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount.Companion.FRACTION_DELIMITER
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount.Companion.of
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.pow

class MoneyAmount private constructor(
    val numerator: Long = 0,
    val denominatorPower: Int = 0,
) : Comparable<MoneyAmount> {

    companion object {
        const val FRACTION_DELIMITER = "."

        fun of(numerator: Long = 0, denominatorPower: Int = 0): MoneyAmount {
            var finalNumerator = numerator
            var finalDenominatorPower = denominatorPower
            while (finalDenominatorPower > 0 && finalNumerator % 10 == 0L) {
                finalDenominatorPower--
                finalNumerator /= 10
            }
            return MoneyAmount(finalNumerator, finalDenominatorPower)
        }

        fun zero() = of()
    }

    // TODO Investigate lazy calculation
    val denominator get() = denominatorPower.toDenominator()
    val integer = numerator / denominator
    val fractional = abs(numerator % denominator)
    val doubleValue get() = numerator.toDouble() / denominator

    fun isNegative() = numerator < 0

    operator fun plus(amount: MoneyAmount): MoneyAmount {
        if (denominatorPower == amount.denominatorPower)
            return of(numerator = numerator + amount.numerator, denominatorPower)

        val bigger: MoneyAmount
        val lesser: MoneyAmount
        if (denominatorPower < amount.denominatorPower) {
            bigger = this
            lesser = amount
        } else {
            lesser = this
            bigger = amount
        }

        return of(
            bigger.numerator
                    * (lesser.denominatorPower - bigger.denominatorPower).toDenominator()
                    + lesser.numerator,
            lesser.denominatorPower,
        )
    }

    operator fun unaryMinus(): MoneyAmount = of(-numerator, denominatorPower)

    operator fun minus(amount: MoneyAmount) = this + -amount

    operator fun times(amount: MoneyAmount) =
        of(
            numerator * amount.numerator,
            denominatorPower + amount.denominatorPower,
        )

    override fun compareTo(other: MoneyAmount): Int {
        return doubleValue.compareTo(other.doubleValue)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MoneyAmount

        if (numerator != other.numerator) return false
        if (denominatorPower != other.denominatorPower) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominatorPower
        return result
    }

    fun toString(
        fractionDelimiter: String = FRACTION_DELIMITER,
        absolute: Boolean = false
    ): String =
        (if (absolute || numerator >= 0) "" else "-") +
                integer.absoluteValue +
                (if (fractional != 0L) "$fractionDelimiter$fractional" else "")

    override fun toString(): String = toString(absolute = false)

    private fun Int.toDenominator() = 10.0.pow(this).toLong()
}

fun Int.toMoneyAmount(): MoneyAmount = of(this.toLong())

fun Long.toMoneyAmount(): MoneyAmount = of(this)

fun Double.toMoneyAmount(): MoneyAmount = toString().toMoneyAmountOrNull()!!

fun String.toMoneyAmount(): MoneyAmount = toMoneyAmountOrNull()!!

fun String.toMoneyAmountOrNull(): MoneyAmount? {
    val isNegative = toDoubleOrNull()?.let { it < 0 } ?: return null
    val integer =
        substringBefore(FRACTION_DELIMITER).toLongOrNull()?.absoluteValue ?: return null
    val fractionString = substringAfter(FRACTION_DELIMITER, missingDelimiterValue = "")
    val fraction =
        if (fractionString.isBlank()) 0
        else fractionString.toLongOrNull() ?: return null

    val denominatorPower = fractionString.indexOfLast { it != '0' } + 1

    return of(
        numerator = (
                integer * 10.0.pow(denominatorPower).toLong() + fraction
                ) * (if (isNegative) -1 else 1),
        denominatorPower = denominatorPower,
    )
}