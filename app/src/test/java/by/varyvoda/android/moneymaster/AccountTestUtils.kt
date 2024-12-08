package by.varyvoda.android.moneymaster

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import java.util.Date


fun fastAccount() =
    Account(
        name = "",
        currencyCode = Currency.BYN.code,
        initialBalance = 0,
        currentBalance = 0,
        themeId = 0L
    )

fun fastOutcome() =
    Expense(
        amount = 0,
        category = Category.Unknown,
        date = Date(),
        description = "Outcome",
        images = listOf()
    )

fun fastIncome() =
    Income(
        income = 0,
        category = Category.Unknown,
        date = Date(),
        description = "Income",
        images = listOf()
    )
