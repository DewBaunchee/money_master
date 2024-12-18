package by.varyvoda.android.moneymaster.config

import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.now
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.OperationRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.CategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.ColorThemeRepository
import by.varyvoda.android.moneymaster.data.service.icons.IconsService
import kotlinx.coroutines.flow.first
import org.kodein.di.DI
import org.kodein.di.instance
import java.util.UUID

suspend fun initializeDependencies(di: DI) {
    val iconsService: IconsService by di.instance()

    val colorThemeRepository: ColorThemeRepository by di.instance()
    insertThemes(colorThemeRepository)

    val accountRepository: AccountRepository by di.instance()
    insertAccounts(accountRepository, colorThemeRepository, iconsService)

    val categoryRepository: CategoryRepository by di.instance()
    insertCategories(categoryRepository, colorThemeRepository, iconsService)

    val operationRepository: OperationRepository by di.instance()
    insertOperations(operationRepository, accountRepository, categoryRepository)
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
            initialBalance = 1000,
            currentBalance = 1000,
            currencyCode = "USD",
            iconRef = iconsService.load("RocketLaunch")!!,
            theme = themes.random(),
        )
    )
    repository.insert(
        Account(
            name = "Second",
            initialBalance = 2000,
            currentBalance = 2000,
            currencyCode = "BYN",
            iconRef = iconsService.load("RocketLaunch")!!,
            theme = themes.random(),
        )
    )
    repository.insert(
        Account(
            name = "Third",
            initialBalance = 3000,
            currentBalance = 3000,
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
    operationRepository: OperationRepository,
    accountRepository: AccountRepository,
    categoryRepository: CategoryRepository
) {
    val accounts = accountRepository.getAll().first()
    val categories = categoryRepository.getAll().first()
    repeat(6) {
        operationRepository.insert(
            Income(
                id = UUID.randomUUID(),
                accountId = accounts.random().id,
                date = now(),
                amount = (1L..200L).random(),
                categoryId = categories.random().id,
                description = "Description",
            )
        )
    }
    repeat(6) {
        operationRepository.insert(
            Expense(
                id = UUID.randomUUID(),
                accountId = accounts.random().id,
                date = now(),
                amount = (1L..200L).random(),
                categoryId = categories.random().id,
                description = "Description",
            )
        )
    }
}