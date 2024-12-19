package by.varyvoda.android.moneymaster.config.database

import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyExchangeRateDao
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.currency.CurrencyExchangeRate
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount

// TODO Create asset of DB
suspend fun initializeDatabase(database: AppRoomDatabase) {
    database.clearAllTables()
    insertCurrencies(database.currencyDao())
    insertCurrencyExchangeRates(database.currencyExchangeRateDao())
}

suspend fun insertCurrencies(dao: CurrencyDao) {
    dao.insert(Currency(code = "USD", name = "American Dollar", symbol = "$"))
    dao.insert(Currency(code = "BYN", name = "Belarusian Rouble", symbol = "BYN"))
}

suspend fun insertCurrencyExchangeRates(dao: CurrencyExchangeRateDao) {
    dao.insert(
        CurrencyExchangeRate(
            soldCurrencyCode = "USD",
            boughtCurrencyCode = "BYN",
            MoneyAmount(numerator = 34405, denominatorPower = 4)
        )
    )
    dao.insert(
        CurrencyExchangeRate(
            soldCurrencyCode = "BYN",
            boughtCurrencyCode = "USD",
            MoneyAmount(numerator = 29, denominatorPower = 2)
        )
    )
}
