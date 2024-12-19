package by.varyvoda.android.moneymaster

import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.toMoneyAmountOrNull
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test


class MoneyAmountTests {

    @Test
    fun `1 string to money amount`() {
        assertAmount(1, 0) { "1".toMoneyAmountOrNull() }
    }

    @Test
    fun `100 string to money amount`() {
        assertAmount(100, 0) { "100".toMoneyAmountOrNull() }
    }

    @Test
    fun `0,01 string to money amount`() {
        assertAmount(1, 2) { "0.01".toMoneyAmountOrNull() }
    }

    @Test
    fun `12,34 string to money amount`() {
        assertAmount(1234, 2) { "12.34".toMoneyAmountOrNull() }
    }

    @Test
    fun `-12,34 string to money amount`() {
        assertAmount(-1234, 2) { "-12.34".toMoneyAmountOrNull() }
    }

    @Test
    fun `12 + 0,01`() {
        assertAmount(1201, 2) {
            MoneyAmount(numerator = 12, denominatorPower = 0) +
                    MoneyAmount(numerator = 1, denominatorPower = 2)
        }
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