package by.varyvoda.android.moneymaster.config

import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.plusDays
import by.varyvoda.android.moneymaster.data.model.domain.toMoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.today
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.OperationRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.CategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.ColorThemeRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.data.service.icons.IconsService
import kotlinx.coroutines.flow.first
import org.kodein.di.DI
import org.kodein.di.instance

suspend fun initializeDependencies(di: DI) {
    val iconsService: IconsService by di.instance()
    val accountService: AccountService by di.instance()

    val colorThemeRepository: ColorThemeRepository by di.instance()
    insertThemes(colorThemeRepository)

    val accountRepository: AccountRepository by di.instance()
    insertAccounts(accountRepository, colorThemeRepository, iconsService)

    val categoryRepository: CategoryRepository by di.instance()
    insertCategories(categoryRepository, colorThemeRepository, iconsService)

    val operationRepository: OperationRepository by di.instance()
    insertOperations(accountService, accountRepository, categoryRepository)
}

private suspend fun insertThemes(repository: ColorThemeRepository) {
    repository.insert(
        ColorTheme(
            name = "Gray",
            colors = listOf(Color(0xFF202335), Color(0xFF5E669B))
        )
    )
    repository.insert(
        ColorTheme(
            name = "Blue",
            colors = listOf(Color(0xFF0253F4), Color(0xFFA228FF))
        )
    )
    repository.insert(
        ColorTheme(
            name = "Purple",
            colors = listOf(Color(0xFF6302F4), Color(0xFFA228FF))
        )
    )
    repository.insert(
        ColorTheme(
            name = "Pink",
            colors = listOf(Color(0xFFF40202), Color(0xFFA228FF))
        )
    )
    repository.insert(
        ColorTheme(
            name = "Orange",
            colors = listOf(Color(0xFFFF5E36), Color(0xFFFF282C))
        )
    )
}

private suspend fun insertAccounts(
    repository: AccountRepository,
    colorThemeRepository: ColorThemeRepository,
    iconsService: IconsService,
) {
    val themes = colorThemeRepository.getAll().first()
    repository.insert(
        Account(
            name = "First",
            initialBalance = 1000.toMoneyAmount(),
            currentBalance = 1000.toMoneyAmount(),
            currencyCode = "USD",
            iconRef = iconsService.load("RocketLaunch")!!,
            theme = themes.random(),
        )
    )
    repository.insert(
        Account(
            name = "Second",
            initialBalance = 2000.toMoneyAmount(),
            currentBalance = 2000.toMoneyAmount(),
            currencyCode = "BYN",
            iconRef = iconsService.load("RocketLaunch")!!,
            theme = themes.random(),
        )
    )
    repository.insert(
        Account(
            name = "Third",
            initialBalance = 3000.toMoneyAmount(),
            currentBalance = 3000.toMoneyAmount(),
            currencyCode = "USD",
            iconRef = iconsService.load("RocketLaunch")!!,
            theme = themes.random(),
        )
    )
}

private suspend fun insertCategories(
    repository: CategoryRepository,
    colorThemeRepository: ColorThemeRepository,
    iconsService: IconsService,
) {
    val themes = colorThemeRepository.getAll().first()
    repository.insert(
        Category(
            name = "Clothes",
            operationType = Operation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        Category(
            name = "Car",
            operationType = Operation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        Category(
            name = "Fuel",
            operationType = Operation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        Category(
            name = "Groceries",
            operationType = Operation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        Category(
            name = "Loan",
            operationType = Operation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        Category(
            name = "Debts",
            operationType = Operation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        Category(
            name = "Tech",
            operationType = Operation.Type.INCOME,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        Category(
            name = "Cafe",
            operationType = Operation.Type.INCOME,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
}

private suspend fun insertOperations(
    accountService: AccountService,
    accountRepository: AccountRepository,
    categoryRepository: CategoryRepository
) {
    val accounts = accountRepository.getAll().first()
    val categories = categoryRepository.getAll().first()
    repeat(6) {
        accountService.addIncome(
            accountId = accounts.random().id,
            date = today().plusDays((1..7).random()),
            amount = (1L..200L).random().toMoneyAmount(),
            categoryId = categories.random().id,
            description = "Description",
            images = listOf()
        )
    }
    repeat(6) {
        accountService.addExpense(
            accountId = accounts.random().id,
            date = today().plusDays((1..7).random()),
            amount = (1L..200L).random().toMoneyAmount(),
            categoryId = categories.random().id,
            description = "Description",
            images = listOf()
        )
    }
    repeat(6) {
        accountService.addTransfer(
            date = today().plusDays((1..7).random()),
            sourceAccountId = accounts.random().id,
            destinationAccountId = accounts.random().id,
            sentAmount = 400.toMoneyAmount(),
            receivedAmount = 400.toMoneyAmount(),
            description = "TODO()"
        )
    }
}