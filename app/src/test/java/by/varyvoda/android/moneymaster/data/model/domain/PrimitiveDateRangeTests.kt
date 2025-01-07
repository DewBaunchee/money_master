package by.varyvoda.android.moneymaster.data.model.domain

import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import kotlin.math.abs

@RunWith(JUnitParamsRunner::class)
class PrimitiveDateRangeTests {

    private val fieldsToCheck = listOf(
        Calendar.YEAR,
        Calendar.MONTH,
        Calendar.DATE,
        Calendar.HOUR_OF_DAY,
        Calendar.MINUTE,
        Calendar.SECOND,
        Calendar.MILLISECOND
    )

    @Test
    @Parameters(value = ["-1", "0", "1", "1000", "-1000"])
    fun primitiveDateToDateRangeByYears(offset: Int) {
        primitiveDateToDateRange(Calendar.YEAR, offset)
    }

    @Test
    @Parameters(value = ["-1", "0", "1", "-12", "12", "1000", "-1000"])
    fun primitiveDateToDateRangeByMonths(offset: Int) {
        primitiveDateToDateRange(Calendar.MONTH, offset)
    }

    @Test
    @Parameters(value = ["-1", "0", "1", "1000", "-1000"])
    fun primitiveDateToDateRangeByDates(offset: Int) {
        primitiveDateToDateRange(Calendar.DATE, offset)
    }

    @Test
    @Parameters(value = ["-1", "0", "1", "-24", "24", "1000", "-1000"])
    fun primitiveDateToDateRangeByHours(offset: Int) {
        primitiveDateToDateRange(Calendar.HOUR, offset)
    }

    @Test
    @Parameters(value = ["-1", "0", "1", "-60", "60", "1000", "-1000"])
    fun primitiveDateToDateRangeByMinutes(offset: Int) {
        primitiveDateToDateRange(Calendar.MINUTE, offset)
    }

    @Test
    @Parameters(value = ["-1", "0", "1", "-60", "60", "1000", "-1000"])
    fun primitiveDateToDateRangeBySeconds(offset: Int) {
        primitiveDateToDateRange(Calendar.SECOND, offset)
    }

    @Test
    @Parameters(value = ["-1", "0", "1", "1000", "-1000"])
    fun primitiveDateToDateRangeByMilliseconds(offset: Int) {
        primitiveDateToDateRange(Calendar.MILLISECOND, offset)
    }

    private fun primitiveDateToDateRange(offsetField: Int, offset: Int) {
        val today = now()
        val dateRange = today.toDateRange(offsetField, offset)

        val (begin, end) = dateRange
        val expectedEnd = begin.toCalendar()
            .apply { add(offsetField, abs(offset)) }
            .toPrimitiveDate()
        fieldsToCheck.forEach {
            if (it == offsetField) {
                assertEquals(expectedEnd, end)
            } else {
                assertEquals(expectedEnd, end)
            }
        }
    }
}