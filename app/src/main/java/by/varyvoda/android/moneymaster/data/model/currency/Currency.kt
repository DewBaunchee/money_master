package by.varyvoda.android.moneymaster.data.model.currency

data class Currency(
    val code: String,
    val name: String,
    val symbol: String
) {
    companion object {
        val USD = Currency(code = "USD", name = "American Dollar", symbol = "$")
        val BYN = Currency(code = "BYN", name = "Belarusian Rouble", symbol = "BYN")
    }

    override fun toString(): String {
        return symbol
    }
}