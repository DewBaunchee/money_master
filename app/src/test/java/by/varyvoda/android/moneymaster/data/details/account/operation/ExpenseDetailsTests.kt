package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.util.randomExpense
import by.varyvoda.android.moneymaster.util.randomExpenseDetails
import org.junit.Assert.assertEquals
import org.junit.Test

class ExpenseDetailsTests {

    @Test
    fun expenseDetails_idTypeDate_gotFromModel() {
        val model = randomExpense()
        randomExpenseDetails().copy(model = model).let {
            assertEquals(model.id, it.id)
            assertEquals(model.type, it.type)
            assertEquals(model.date, it.date)
        }
    }
}