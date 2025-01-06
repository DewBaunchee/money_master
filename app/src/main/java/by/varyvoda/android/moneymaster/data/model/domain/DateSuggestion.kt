package by.varyvoda.android.moneymaster.data.model.domain

data class DateSuggestion(
    val label: String,
    val date: PrimitiveDate
) {

    companion object {

        fun default() = listOf(
            DateSuggestion(
                "Today",
                today()
            ),
            DateSuggestion(
                "Yesterday",
                yesterday()
            ),
        )
    }
}