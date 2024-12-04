package by.varyvoda.android.moneymaster.config.database

import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.model.currency.Currency

// TODO Create asset of DB
suspend fun initializeDatabase(database: AppRoomDatabase) {
    database.clearAllTables()
    insertCurrencies(database.currencyDao())
}

suspend fun insertCurrencies(dao: CurrencyDao) {
    dao.insert(Currency(code = "USD", name = "American Dollar", symbol = "$"))
    dao.insert(Currency(code = "BYN", name = "Belarusian Rouble", symbol = "BYN"))
}
