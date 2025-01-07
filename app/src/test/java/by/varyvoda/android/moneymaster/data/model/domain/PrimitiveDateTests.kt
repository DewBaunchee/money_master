package by.varyvoda.android.moneymaster.data.model.domain

import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

fun randomCalendar() = Calendar.getInstance().also {
    it.set(Calendar.YEAR, (1990..2100).random())
    it.set(Calendar.MONTH, (1..12).random())
    it.set(Calendar.DATE, (1..28).random())
    it.set(Calendar.HOUR_OF_DAY, (0..23).random())
    it.set(Calendar.MINUTE, (0..59).random())
    it.set(Calendar.SECOND, (0..59).random())
    it.set(Calendar.MILLISECOND, (0..999).random())
}

@RunWith(JUnitParamsRunner::class)
class PrimitiveDateTests {

    @Test
    fun `now to calendar to primitive date`() {
        val date = now()
        assertEquals(date, date.toCalendar().toPrimitiveDate())
    }

    @Test
    fun `yesterday test`() {
        val today = today()
        val yesterday = yesterday()
        assertEquals(today - 24 * 60 * 60 * 1000, yesterday)
    }

    @Test
    @Parameters(
        value = [
            "1999, 11, 3, 23, 59, 59, 999",
            "1999, 11, 3, 0, 0, 0, 0",
        ]
    )
    fun `only date test`(
        year: Int,
        month: Int,
        date: Int,
        hours: Int,
        minutes: Int,
        seconds: Int,
        millis: Int
    ) {
        val calendar = getCalendar(year, month, date, hours, minutes, seconds, millis)
        val onlyDate = calendar.toPrimitiveDate().onlyDate().toCalendar()
        assertEquals(calendar.get(Calendar.YEAR), onlyDate.get(Calendar.YEAR))
        assertEquals(calendar.get(Calendar.MONTH), onlyDate.get(Calendar.MONTH))
        assertEquals(calendar.get(Calendar.DATE), onlyDate.get(Calendar.DATE))
    }

    private fun getCalendar(
        year: Int,
        month: Int,
        date: Int,
        hours: Int,
        minutes: Int,
        seconds: Int,
        millis: Int
    ) = Calendar.getInstance().also {
        it.set(Calendar.YEAR, year)
        it.set(Calendar.MONTH, month)
        it.set(Calendar.DATE, date)
        it.set(Calendar.HOUR_OF_DAY, hours)
        it.set(Calendar.MINUTE, minutes)
        it.set(Calendar.SECOND, seconds)
        it.set(Calendar.MILLISECOND, millis)
    }
}