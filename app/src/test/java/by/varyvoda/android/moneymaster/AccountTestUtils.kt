package by.varyvoda.android.moneymaster

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountExpense
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountIncome
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountMutationCategory
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
    AccountExpense(
        amount = 0,
        category = AccountMutationCategory.Unknown,
        date = Date(),
        description = "Outcome",
        images = listOf()
    )

fun fastIncome() =
    AccountIncome(
        income = 0,
        category = AccountMutationCategory.Unknown,
        date = Date(),
        description = "Income",
        images = listOf()
    )
