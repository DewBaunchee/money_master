package by.varyvoda.android.moneymaster.data.model.domain

import kotlin.math.max
import kotlin.math.min

typealias PrimitiveDateRange = Pair<PrimitiveDate, PrimitiveDate>

fun PrimitiveDate.toDateRange(offsetField: Int, offset: Int) =
    toCalendar().apply { add(offsetField, offset) }
        .toPrimitiveDate().let { Pair(min(it, this), max(it, this)) }

fun PrimitiveDateRange.toDateRangeString(format: String) =
    "${first.toDateString(format)} - ${second.toDateString(format)}"