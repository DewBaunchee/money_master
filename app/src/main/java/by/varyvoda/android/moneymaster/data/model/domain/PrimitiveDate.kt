package by.varyvoda.android.moneymaster.data.model.domain

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

typealias PrimitiveDate = Long

fun now(): PrimitiveDate =
    Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis

fun PrimitiveDate.local() = this + TimeZone.getTimeZone(ZoneId.systemDefault()).rawOffset

fun Calendar.toPrimitiveDate(): PrimitiveDate = timeInMillis

fun PrimitiveDate.toCalendar(): Calendar = Calendar.getInstance().also { it.timeInMillis = this }

fun PrimitiveDate.onlyDate(): PrimitiveDate = toCalendar().let { calendar ->
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.toPrimitiveDate()
}

fun today(): PrimitiveDate = now().onlyDate()

fun yesterday(): PrimitiveDate = today().plusDays(-1)

fun PrimitiveDate.plusDays(amount: Int): PrimitiveDate {
    return toCalendar().also { it.add(Calendar.DATE, amount) }.toPrimitiveDate()
}

fun PrimitiveDate.plusHours(amount: Int): PrimitiveDate {
    return toCalendar().also { it.add(Calendar.HOUR, amount) }.toPrimitiveDate()
}

fun PrimitiveDate.toDateString(format: String): String =
    SimpleDateFormat(format, Locale.getDefault())
        .format(Date(this))