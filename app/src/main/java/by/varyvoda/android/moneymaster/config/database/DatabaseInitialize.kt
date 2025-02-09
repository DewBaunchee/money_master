package by.varyvoda.android.moneymaster.config.database

import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyExchangeRateDao
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.currency.CurrencyExchangeRate
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount

// TODO Create asset of DB
suspend fun initializeDatabase(database: AppRoomDatabase) {
    insertCurrencies(database.currencyDao())
    insertCurrencyExchangeRates(database.currencyExchangeRateDao())
}

suspend fun insertCurrencies(dao: CurrencyDao) {
    dao.upsert(
        Currency.USD,
        Currency.BYN,
        Currency.EUR
    )
}

suspend fun insertCurrencyExchangeRates(dao: CurrencyExchangeRateDao) {
    dao.upsert(
        CurrencyExchangeRate(
            soldCurrencyCode = Currency.USD.code,
            boughtCurrencyCode = Currency.BYN.code,
            MoneyAmount.of(numerator = 328, denominatorPower = 2)
        ),
        CurrencyExchangeRate(
            soldCurrencyCode = Currency.BYN.code,
            boughtCurrencyCode = Currency.USD.code,
            MoneyAmount.of(numerator = 30, denominatorPower = 2)
        ),
        CurrencyExchangeRate(
            soldCurrencyCode = Currency.EUR.code,
            boughtCurrencyCode = Currency.USD.code,
            MoneyAmount.of(numerator = 105, denominatorPower = 2)
        ),
        CurrencyExchangeRate(
            soldCurrencyCode = Currency.USD.code,
            boughtCurrencyCode = Currency.EUR.code,
            MoneyAmount.of(numerator = 97, denominatorPower = 2)
        ),
        CurrencyExchangeRate(
            soldCurrencyCode = Currency.USD.code,
            boughtCurrencyCode = Currency.BYN.code,
            MoneyAmount.of(numerator = 339, denominatorPower = 2)
        ),
        CurrencyExchangeRate(
            soldCurrencyCode = Currency.BYN.code,
            boughtCurrencyCode = Currency.EUR.code,
            MoneyAmount.of(numerator = 30, denominatorPower = 2)
        )
    )
}