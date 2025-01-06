package by.varyvoda.android.moneymaster.data.model.domain

import java.util.Calendar

data class DateRangeSuggestion(
    val label: String,
    val range: PrimitiveDateRange,
) {

    companion object {

        fun default() = listOf(
            DateRangeSuggestion(
                "1d",
                today().toDateRange(Calendar.DATE, -1)
            ),
            DateRangeSuggestion(
                "1w",
                today().toDateRange(Calendar.WEEK_OF_YEAR, -1)
            ),
            DateRangeSuggestion(
                "1m",
                today().toDateRange(Calendar.MONTH, -1)
            ),
            DateRangeSuggestion(
                "3m",
                today().toDateRange(Calendar.MONTH, -3)
            ),
            DateRangeSuggestion(
                "1y",
                today().toDateRange(Calendar.YEAR, -1)
            ),
        )
    }
}