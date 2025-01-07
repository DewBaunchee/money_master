package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.util.randomIncome
import by.varyvoda.android.moneymaster.util.randomIncomeDetails
import org.junit.Assert.assertEquals
import org.junit.Test

class IncomeDetailsTests {

    @Test
    fun incomeDetails_idTypeDate_gotFromModel() {
        val model = randomIncome()
        randomIncomeDetails().copy(model = model).let {
            assertEquals(model.id, it.id)
            assertEquals(model.type, it.type)
            assertEquals(model.date, it.date)
        }
    }
}