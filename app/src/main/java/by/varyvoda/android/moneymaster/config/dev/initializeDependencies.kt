package by.varyvoda.android.moneymaster.config.dev

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DryCleaning
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.MobileFriendly
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material.icons.filled.Wallet
import by.varyvoda.android.moneymaster.data.dao.account.operation.CategoryDao
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.plusDays
import by.varyvoda.android.moneymaster.data.model.domain.toMoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.today
import by.varyvoda.android.moneymaster.data.model.icon.IconRef.Companion.toIconRef
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

    val categoryDao: CategoryDao by di.instance()
    val categoryRepository: CategoryRepository by di.instance()
    insertCategories(categoryDao, colorThemeRepository)

    return

    val accountRepository: AccountRepository by di.instance()
    insertAccounts(accountRepository, colorThemeRepository, iconsService)

    val operationRepository: OperationRepository by di.instance()
    insertOperations(accountService, accountRepository, categoryRepository)
}

private suspend fun insertAccounts(
    repository: AccountRepository,
    colorThemeRepository: ColorThemeRepository,
    iconsService: IconsService,
) {
    val themes = colorThemeRepository.getAll().first()
    repository.upsert(
        Account(
            name = "First",
            initialBalance = 1000.toMoneyAmount(),
            currentBalance = 1000.toMoneyAmount(),
            currencyCode = Currency.USD.code,
            iconRef = iconsService.load("RocketLaunch")!!,
            theme = themes.random(),
        )
    )
    repository.upsert(
        Account(
            name = "Second",
            initialBalance = 2000.toMoneyAmount(),
            currentBalance = 2000.toMoneyAmount(),
            currencyCode = Currency.BYN.code,
            iconRef = iconsService.load("RocketLaunch")!!,
            theme = themes.random(),
        )
    )
    repository.upsert(
        Account(
            name = "Third",
            initialBalance = 3000.toMoneyAmount(),
            currentBalance = 3000.toMoneyAmount(),
            currencyCode = Currency.USD.code,
            iconRef = iconsService.load("RocketLaunch")!!,
            theme = themes.random(),
        )
    )
}

private suspend fun insertCategories(
    dao: CategoryDao,
    colorThemeRepository: ColorThemeRepository,
) {
    val themes = colorThemeRepository.getAll().first()
    dao.upsert(
        Category(
            id = 1,
            operationType = Operation.Type.EXPENSE,
            name = "Home",
            iconRef = Icons.Filled.Home.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 2,
            operationType = Operation.Type.EXPENSE,
            name = "Cafe",
            iconRef = Icons.Filled.Restaurant.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 3,
            operationType = Operation.Type.EXPENSE,
            name = "Taxi",
            iconRef = Icons.Filled.LocalTaxi.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 4,
            operationType = Operation.Type.EXPENSE,
            name = "Clothes",
            iconRef = Icons.Filled.DryCleaning.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 5,
            operationType = Operation.Type.EXPENSE,
            name = "Leisure",
            iconRef = Icons.Filled.Wallet.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 6,
            operationType = Operation.Type.EXPENSE,
            name = "Fuel",
            iconRef = Icons.Filled.LocalGasStation.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 7,
            operationType = Operation.Type.EXPENSE,
            name = "Car",
            iconRef = Icons.Filled.DirectionsCar.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 8,
            operationType = Operation.Type.EXPENSE,
            name = "Transport",
            iconRef = Icons.Filled.DirectionsBus.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 9,
            operationType = Operation.Type.EXPENSE,
            name = "Lunch",
            iconRef = Icons.Filled.LunchDining.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 10,
            operationType = Operation.Type.EXPENSE,
            name = "Groceries",
            iconRef = Icons.Filled.ShoppingCart.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 11,
            operationType = Operation.Type.EXPENSE,
            name = "Family",
            iconRef = Icons.Filled.FamilyRestroom.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 12,
            operationType = Operation.Type.EXPENSE,
            name = "Health",
            iconRef = Icons.Filled.MonitorHeart.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 13,
            operationType = Operation.Type.EXPENSE,
            name = "Gift",
            iconRef = Icons.Filled.Celebration.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 14,
            operationType = Operation.Type.EXPENSE,
            name = "Education",
            iconRef = Icons.Filled.School.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 15,
            operationType = Operation.Type.EXPENSE,
            name = "Fitness",
            iconRef = Icons.Filled.FitnessCenter.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 16,
            operationType = Operation.Type.EXPENSE,
            name = "Cinema",
            iconRef = Icons.Filled.Theaters.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 17,
            operationType = Operation.Type.EXPENSE,
            name = "Beauty",
            iconRef = Icons.Filled.Brush.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 18,
            operationType = Operation.Type.EXPENSE,
            name = "Unspecified",
            iconRef = Icons.Filled.QuestionMark.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 19,
            operationType = Operation.Type.EXPENSE,
            name = "Subscriptions",
            iconRef = Icons.Filled.CardMembership.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 20,
            operationType = Operation.Type.EXPENSE,
            name = "Mobile",
            iconRef = Icons.Filled.MobileFriendly.toIconRef(),
            colorTheme = themes.random()
        ),
        Category(
            id = 21,
            operationType = Operation.Type.INCOME,
            name = "Paycheck",
            iconRef = Icons.Filled.AddCard.toIconRef(),
            colorTheme = themes.random()
        ),
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
            date = today().plusDays(-(1..7).random()),
            amount = (1L..200L).random().toMoneyAmount(),
            categoryId = categories.random().id,
            description = "Description",
            images = listOf()
        )
    }
    repeat(6) {
        accountService.addExpense(
            accountId = accounts.random().id,
            date = today().plusDays(-(1..7).random()),
            amount = (1L..200L).random().toMoneyAmount(),
            categoryId = categories.random().id,
            description = "Description",
            images = listOf()
        )
    }
    repeat(6) {
        accountService.addTransfer(
            date = today().plusDays(-(1..7).random()),
            sourceAccountId = accounts.random().id,
            destinationAccountId = accounts.random().id,
            sentAmount = 400.toMoneyAmount(),
            receivedAmount = 400.toMoneyAmount(),
            description = "TODO()"
        )
    }
}