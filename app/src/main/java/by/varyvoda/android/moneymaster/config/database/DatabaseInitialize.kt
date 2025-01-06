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
    dao.insert(Currency.USD)
    dao.insert(Currency.BYN)
}

suspend fun insertCurrencyExchangeRates(dao: CurrencyExchangeRateDao) {
    dao.insert(
        CurrencyExchangeRate(
            soldCurrencyCode = Currency.USD.code,
            boughtCurrencyCode = Currency.BYN.code,
            MoneyAmount(numerator = 34405, denominatorPower = 4)
        )
    )
    dao.insert(
        CurrencyExchangeRate(
            soldCurrencyCode = Currency.BYN.code,
            boughtCurrencyCode = Currency.USD.code,
            MoneyAmount(numerator = 29, denominatorPower = 2)
        )
    )
}
