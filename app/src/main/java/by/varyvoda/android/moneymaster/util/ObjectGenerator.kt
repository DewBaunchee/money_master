package by.varyvoda.android.moneymaster.util

import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.ExpenseDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.IncomeDetails
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.BalanceEdit
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.operation.Transfer
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.plusDays
import by.varyvoda.android.moneymaster.data.model.domain.today
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import kotlin.math.pow

private fun <T> T.toFlow() = flowOf(this)

fun randomId() = (1L..1_000_000L).random()

fun randomUUID() = UUID.randomUUID()!!

fun randomDate() = today().plusDays((-10..10).random())

fun randomMoneyAmount() =
    MoneyAmount.of(
        numerator = (-1_000L..1_000L).random(),
        denominatorPower = 10.0.pow((1..10).random()).toInt()
    )

fun randomString(length: Int = (1..100).random()): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun randomOperationType() = Operation.Type.entries.toTypedArray().random()

fun randomIncome() =
    Income(
        id = randomUUID(),
        date = randomDate(),
        accountId = randomId(),
        amount = randomMoneyAmount(),
        categoryId = randomId(),
        description = randomString()
    )

fun randomExpense() =
    Expense(
        id = randomUUID(),
        date = randomDate(),
        accountId = randomId(),
        amount = randomMoneyAmount(),
        categoryId = randomId(),
        description = randomString()
    )

fun randomTransfer() =
    Transfer(
        id = randomUUID(),
        date = randomDate(),
        sourceAccountId = randomId(),
        destinationAccountId = randomId(),
        sentAmount = randomMoneyAmount(),
        receivedAmount = randomMoneyAmount(),
        description = randomString()
    )

fun randomBalanceEdit() =
    BalanceEdit(
        id = randomUUID(),
        date = randomDate(),
        accountId = randomId(),
        correctionValue = randomMoneyAmount(),
        oldValue = randomMoneyAmount()
    )

fun randomIncomeDetails() =
    IncomeDetails(
        model = randomIncome(),
        category = randomCategory().toFlow(),
        account = randomAccountDetails().toFlow()
    )

fun randomExpenseDetails() =
    ExpenseDetails(
        model = randomExpense(),
        category = randomCategory().toFlow(),
        account = randomAccountDetails().toFlow()
    )

fun randomIconRef() =
    IconRef.List.random()

private val colorsToRandomize =
    listOf(Color.Red, Color.Green, Color.Blue, Color.Black, Color.Cyan, Color.Green, Color.Magenta)

fun randomColor() = colorsToRandomize.random()

fun randomColorTheme() =
    ColorTheme(
        name = randomString(),
        colors = listOf(randomColor(), randomColor())
    )

fun randomCategory() =
    Category(
        id = randomId(),
        operationType = randomOperationType(),
        name = randomString(),
        iconRef = randomIconRef(),
        colorTheme = randomColorTheme()
    )

fun randomCurrencyCode() = randomString(3)

fun randomCurrency() =
    Currency(
        code = randomCurrencyCode(),
        name = randomString(),
        symbol = randomString(5)
    )

fun randomAccount() =
    Account(
        id = randomId(),
        name = randomString(),
        initialBalance = randomMoneyAmount(),
        currentBalance = randomMoneyAmount(),
        currencyCode = randomCurrencyCode(),
        iconRef = randomIconRef(),
        theme = randomColorTheme()
    )

fun randomAccountDetails() =
    AccountDetails(
        model = randomAccount(),
        currency = randomCurrency().toFlow()
    )