package by.varyvoda.android.moneymaster.config

import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperationCategory
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.AccountOperationCategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.ColorThemeRepository
import by.varyvoda.android.moneymaster.data.service.icons.IconsService
import kotlinx.coroutines.flow.first
import org.kodein.di.DI
import org.kodein.di.instance

suspend fun initializeDependencies(di: DI) {
    val iconsService: IconsService by di.instance()

    val colorThemeRepository: ColorThemeRepository by di.instance()
    insertThemes(colorThemeRepository)

    val accountRepository: AccountRepository by di.instance()
    insertAccounts(accountRepository, colorThemeRepository, iconsService)

    val accountOperationCategoryRepository: AccountOperationCategoryRepository by di.instance()
    insertCategories(accountOperationCategoryRepository, colorThemeRepository, iconsService)
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
    repository: AccountOperationCategoryRepository,
    colorThemeRepository: ColorThemeRepository,
    iconsService: IconsService,
) {
    val themes = colorThemeRepository.getAll().first()
    repository.insert(
        AccountOperationCategory(
            name = "Clothes",
            operationType = AccountOperation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        AccountOperationCategory(
            name = "Car",
            operationType = AccountOperation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        AccountOperationCategory(
            name = "Fuel",
            operationType = AccountOperation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        AccountOperationCategory(
            name = "Groceries",
            operationType = AccountOperation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        AccountOperationCategory(
            name = "Loan",
            operationType = AccountOperation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        AccountOperationCategory(
            name = "Debts",
            operationType = AccountOperation.Type.EXPENSE,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        AccountOperationCategory(
            name = "Tech",
            operationType = AccountOperation.Type.INCOME,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
    repository.insert(
        AccountOperationCategory(
            name = "Cafe",
            operationType = AccountOperation.Type.INCOME,
            iconRef = iconsService.load("RocketLaunch")!!,
            colorTheme = themes.random(),
        )
    )
}