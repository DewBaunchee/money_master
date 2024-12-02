package by.varyvoda.android.moneymaster.data.model.domain

import java.util.Calendar

typealias PrimitiveDate = Long

fun now(): PrimitiveDate {
    return Calendar.getInstance().timeInMillis
}

fun Calendar.toPrimitiveDate(): PrimitiveDate {
    return timeInMillis
}

fun PrimitiveDate.toCalendar(): Calendar {
    return Calendar.getInstance().also { it.timeInMillis = this }
}

fun PrimitiveDate.onlyDate(): PrimitiveDate {
    return toCalendar().let { calendar ->
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.toPrimitiveDate()
    }
}

fun today(): PrimitiveDate {
    return now().onlyDate()
}

fun yesterday(): PrimitiveDate {
    return now().onlyDate().plusDays(-1)
}

fun PrimitiveDate.plusDays(amount: Int): PrimitiveDate {
    return toCalendar().also { it.add(Calendar.DATE, amount) }.toPrimitiveDate()
}

fun PrimitiveDate.plusHours(amount: Int): PrimitiveDate {
    return toCalendar().also { it.add(Calendar.HOUR, amount) }.toPrimitiveDate()
}