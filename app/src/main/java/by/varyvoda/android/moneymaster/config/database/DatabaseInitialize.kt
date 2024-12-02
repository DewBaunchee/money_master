package by.varyvoda.android.moneymaster.config.database

import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountOperationCategoryDao
import by.varyvoda.android.moneymaster.data.dao.account.theme.AccountThemeDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperationCategory
import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency

// TODO Create asset of DB
suspend fun initializeDatabase(database: AppRoomDatabase) {
    database.clearAllTables()
    insertCurrencies(database.currencyDao())
    insertThemes(database.accountThemeDao())
    insertAccounts(database.accountDao())
    insertCategories(database.accountOperationCategoryDao())
}

suspend fun insertCurrencies(dao: CurrencyDao) {
    dao.insert(Currency(code = "USD", name = "American Dollar", symbol = "$"))
    dao.insert(Currency(code = "BYN", name = "Belarusian Rouble", symbol = "BYN"))
}

suspend fun insertThemes(dao: AccountThemeDao) {
    dao.insert(
        AccountTheme(
            id = 1,
            name = "Gray",
            gradientColors = listOf(Color(0xFF202335), Color(0xFF5E669B))
        )
    )
    dao.insert(
        AccountTheme(
            id = 2,
            name = "Blue",
            gradientColors = listOf(Color(0xFF0253F4), Color(0xFFA228FF))
        )
    )
    dao.insert(
        AccountTheme(
            id = 3,
            name = "Purple",
            gradientColors = listOf(Color(0xFF6302F4), Color(0xFFA228FF))
        )
    )
    dao.insert(
        AccountTheme(
            id = 4,
            name = "Pink",
            gradientColors = listOf(Color(0xFFF40202), Color(0xFFA228FF))
        )
    )
    dao.insert(
        AccountTheme(
            id = 5,
            name = "Orange",
            gradientColors = listOf(Color(0xFFFF5E36), Color(0xFFFF282C))
        )
    )
}


suspend fun insertAccounts(dao: AccountDao) {
    dao.insert(
        Account(
            name = "First",
            initialBalance = 1000,
            currentBalance = 1000,
            currencyCode = "USD",
            themeId = 1,
        )
    )
    dao.insert(
        Account(
            name = "Second",
            initialBalance = 2000,
            currentBalance = 2000,
            currencyCode = "BYN",
            themeId = 2,
        )
    )
    dao.insert(
        Account(
            name = "Third",
            initialBalance = 3000,
            currentBalance = 3000,
            currencyCode = "USD",
            themeId = 3,
        )
    )
}

suspend fun insertCategories(dao: AccountOperationCategoryDao) {
    dao.insert(
        AccountOperationCategory(
            name = "Clothes",
            icon = 0,
            color = Color.Red,
        )
    )
    dao.insert(
        AccountOperationCategory(
            name = "Car",
            icon = 0,
            color = Color.Blue,
        )
    )
    dao.insert(
        AccountOperationCategory(
            name = "Fuel",
            icon = 0,
            color = Color.Black,
        )
    )
    dao.insert(
        AccountOperationCategory(
            name = "Groceries",
            icon = 0,
            color = Color.Green,
        )
    )
    dao.insert(
        AccountOperationCategory(
            name = "Loan",
            icon = 0,
            color = Color.Cyan,
        )
    )
    dao.insert(
        AccountOperationCategory(
            name = "Debts",
            icon = 0,
            color = Color.Magenta,
        )
    )
    dao.insert(
        AccountOperationCategory(
            name = "Tech",
            icon = 0,
            color = Color.Yellow,
        )
    )
    dao.insert(
        AccountOperationCategory(
            name = "Cafe",
            icon = 0,
            color = Color.DarkGray,
        )
    )
}