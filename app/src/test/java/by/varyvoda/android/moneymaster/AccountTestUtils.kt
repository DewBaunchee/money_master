package by.varyvoda.android.moneymaster

import android.icu.util.Calendar
import by.varyvoda.android.moneymaster.data.account.Account
import by.varyvoda.android.moneymaster.data.account.AccountIncome
import by.varyvoda.android.moneymaster.data.account.AccountMutationCategory
import by.varyvoda.android.moneymaster.data.account.AccountOutcome
import by.varyvoda.android.moneymaster.data.currency.Currency
import java.util.Date

fun fastAccount() =
    Account(
        name = "",
        currency = Currency.BYN,
        initialBalance = 0,
    )

fun fastOutcome() =
    AccountOutcome(
        outcome = 0,
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
