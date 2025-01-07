package by.varyvoda.android.moneymaster.data.model.domain

import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

@RunWith(JUnitParamsRunner::class)
class MoneyAmountTests {

    @Test
    @Parameters(
        value = [
            "1, 1",
            "12, 12",
            "13, 13.45",
            "0, 0.45",
            "-42141, -42141.4214124",
        ]
    )
    fun `{integer part} from {source string}`(expectedInteger: Long, sourceString: String) {
        assertEquals(expectedInteger, sourceString.toMoneyAmount().integer)
    }

    @Test
    @Parameters(
        value = [
            "0, 1",
            "0, 12",
            "45, 13.45",
            "45, 0.45",
            "4214124, -42141.4214124",
        ]
    )
    fun `{frictional part} from {source string}`(expectedFractional: Long, sourceString: String) {
        assertEquals(expectedFractional, sourceString.toMoneyAmount().fractional)
    }

    @Test
    @Parameters(
        value = [
            "1, 0, 1",
            "1, 2, 0.01",
            "100, 0, 100",
            "1234, 2, 12.34",
            "-1234, 2, -12.34",
        ]
    )
    fun `expected {numerator} and {denominator} from {source string}`(
        expectedNumerator: Long,
        expectedDenominatorPower: Int,
        sourceString: String
    ) {
        assertAmount(
            expectedNumerator, expectedDenominatorPower
        ) { sourceString.toMoneyAmountOrNull() }
    }

    @Test
    @Parameters(
        value = [
            "1", "2.3", "-2.3", "0.45", "-0.45"
        ]
    )
    fun `expected {double value} from {source string}`(
        double: Double
    ) {
        assertEquals(double, double.toMoneyAmount().doubleValue, 1e-6)
    }

    @Test
    @Parameters(
        value = [
            "false, 1", "false, 2.3", "true, -2.3", "false, 0.45", "true, -0.45"
        ]
    )
    fun `expected {isNegative} from {source string}`(
        isNegative: Boolean,
        double: Double
    ) {
        assertEquals(isNegative, double.toMoneyAmount().isNegative())
    }

    @Test
    @Parameters(
        value = [
            "12, 0.01, 1201, 2",
            "0.0, 0.0, 0, 0",
            "100, 0, 100, 0",
            "5.23, 4.44, 967, 2",
            "5.23, 4.445, 9675, 3",
            "5.233, 1.23, 6463, 3",
        ]
    )
    fun `{first} + {second} = expected {numerator} and {denominator}`(
        firstSource: String,
        secondSource: String,
        expectedNumerator: Long,
        expectedDenominatorPower: Int,
    ) {
        assertAmount(expectedNumerator, expectedDenominatorPower) {
            firstSource.toMoneyAmount() + secondSource.toMoneyAmount()
        }
    }

    @Test
    @Parameters(
        value = [
            "12, 0.01, 1199, 2",
            "0.0, 0.0, 0, 0",
            "100, 0, 100, 0",
            "5.89, 2.22, 367, 2",
            "5.89, 4.445, 1445, 3",
            "5.233, 1.23, 4003, 3",
        ]
    )
    fun `{first} - {second} = expected {numerator} and {denominator}`(
        firstSource: String,
        secondSource: String,
        expectedNumerator: Long,
        expectedDenominatorPower: Int,
    ) {
        assertAmount(expectedNumerator, expectedDenominatorPower) {
            firstSource.toMoneyAmount() - secondSource.toMoneyAmount()
        }
    }

    @Test
    @Parameters(
        value = [
            "0.0, 0.0, 0, 0",
            "100, 0, 0, 0",
            "0, 100, 0, 0",
            "2, 3, 6, 0",
            "5, -4, -20, 0",
            "0.5, 2, 1, 0",
            "-0.25, 8, -2, 0",
            "5.89, 2.22, 130758, 4",
            "5.89, 4.445, 2618105, 5",
            "5.233, 1.23, 643659, 5",
        ]
    )
    fun `{first} x {second} = expected {numerator} and {denominator}`(
        firstSource: String,
        secondSource: String,
        expectedNumerator: Long,
        expectedDenominatorPower: Int,
    ) {
        assertAmount(expectedNumerator, expectedDenominatorPower) {
            firstSource.toMoneyAmount() * secondSource.toMoneyAmount()
        }
    }

    @Test
    @Parameters(
        value = [
            "0.0, 0, 0",
            "-0.0, 0, 0",
            "-1, 1, -1",
            "1, -1, 1",
            "12.3, 1.23, 1",
            "0.055, 0.55, -1",
        ]
    )
    fun `{first} compareTo {second} = {result}`(
        firstSource: String,
        secondSource: String,
        result: Int,
    ) {
        assertEquals(result, firstSource.toMoneyAmount().compareTo(secondSource.toMoneyAmount()))
    }

    @Test
    @Parameters(
        value = [
            "0", "-0", "1", "-1", "1234", "-12341"
        ]
    )
    fun `{int} to money amount then to string = {int}`(
        int: Int,
    ) {
        assertEquals(int, int.toMoneyAmount().toString().toInt())
    }

    @Test
    @Parameters(
        value = [
            "0", "-0", "1", "-1", "1234", "-12341"
        ]
    )
    fun `{long} to money amount then to string = {long}`(
        long: Long,
    ) {
        assertEquals(long, long.toMoneyAmount().toString().toLong())
    }

    @Test
    @Parameters(
        value = [
            "0", "0.0", "-0.0", "1", "-3", "3.45", "0.55", "-0.2323", "-123", "-123.45"
        ]
    )
    fun `{double} to money amount then to string = {double}`(
        double: Double,
    ) {
        assertEquals(double, double.toMoneyAmount().toString().toDouble(), 1e-6)
    }

    @Test
    @Parameters(
        value = [
            "0", "0.0", "-0.0", "1", "-3", "3.45", "0.55", "-0.2323", "-123", "-123.45"
        ]
    )
    fun `{double} to absolute string = absolute {double}`(
        double: Double,
    ) {
        assertEquals(abs(double), double.toMoneyAmount().toString(absolute = true).toDouble(), 1e-6)
    }

    @Test
    @Parameters(
        value = [
            "0, 0",
            "0, -0",
            "0.0, 0.0",
            "0.0, -0.0",
            "-12, 12",
            "12, -12",
            "-0.45, 0.45",
            "0.45, -0.45",
            "-123.45, 123.45",
            "123.45, -123.45",
        ]
    )
    fun `test unary minus`(
        expectedDouble: Double,
        stringSource: String,
    ) {
        assertEquals(expectedDouble, (-stringSource.toMoneyAmount()).doubleValue, 1e-6)
    }

    private fun assertAmount(
        expectedNumerator: Long,
        expectedDenominatorPower: Int,
        creator: () -> MoneyAmount?
    ) {
        val moneyAmount = creator()

        assertNotNull(moneyAmount)
        assertEquals(expectedNumerator, moneyAmount!!.numerator)
        assertEquals(expectedDenominatorPower, moneyAmount.denominatorPower)
    }
}