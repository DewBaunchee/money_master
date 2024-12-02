package by.varyvoda.android.moneymaster.data.model.domain

data class DateSuggestion(
    val label: String,
    val date: PrimitiveDate
) {

    companion object {

        val DEFAULT = listOf(
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