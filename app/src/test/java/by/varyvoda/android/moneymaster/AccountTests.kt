package by.varyvoda.android.moneymaster

import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountBalanceEdit
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AccountTests {

    @Test
    fun account100_expense150_resultMinus50() {
        val expectedBalance = -50
        val account =
            fastAccount().copy(
                by.varyvoda.android.moneymaster.data.model.account.Account.initialBalance = 100,
                mutations = listOf(fastOutcome().copy(outcome = 150))
            )

        assertEquals(expectedBalance, account.balance)
    }

    @Test
    fun account100_income150_result250() {
        val expectedBalance = 250
        val account =
            fastAccount().copy(
                initialBalance = 100,
                mutations = listOf(fastIncome().copy(income = 150))
            )

        assertEquals(expectedBalance, account.balance)
    }

    @Test
    fun account100_expense50income150_result200() {
        val expectedBalance = 200
        val account =
            fastAccount().copy(
                initialBalance = 100,
                mutations = listOf(
                    fastOutcome().copy(outcome = 50),
                    fastIncome().copy(income = 150),
                )
            )

        assertEquals(expectedBalance, account.balance)
    }

    @Test
    fun account100_balanceEdit200_result200() {
        val expectedBalance = 200
        val account =
            fastAccount().copy(
                initialBalance = 100,
                mutations = listOf(AccountBalanceEdit(expectedBalance, 100))
            )

        assertEquals(expectedBalance, account.balance)
    }
}