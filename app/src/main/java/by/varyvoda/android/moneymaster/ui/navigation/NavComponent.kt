package by.varyvoda.android.moneymaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.base.NavigateBackEffect
import by.varyvoda.android.moneymaster.ui.base.NavigateBackToEffect
import by.varyvoda.android.moneymaster.ui.base.NavigateToEffect
import by.varyvoda.android.moneymaster.ui.base.NavigateUpEffect
import by.varyvoda.android.moneymaster.ui.effect.appEffects
import by.varyvoda.android.moneymaster.ui.effect.handle
import by.varyvoda.android.moneymaster.ui.screen.account.addincome.AddIncomeDestination
import by.varyvoda.android.moneymaster.ui.screen.account.addincome.AddIncomeScreen
import by.varyvoda.android.moneymaster.ui.screen.account.addincome.AddIncomeViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.category.AccountOperationCategoryEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.category.AccountOperationCategoryEditScreen
import by.varyvoda.android.moneymaster.ui.screen.account.category.AccountOperationCategoryEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountCreationScreen
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseDestination
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseScreen
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseViewModel
import by.varyvoda.android.moneymaster.ui.screen.home.HomeDestination
import by.varyvoda.android.moneymaster.ui.screen.home.HomeScreen
import by.varyvoda.android.moneymaster.ui.screen.home.HomeViewModel
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun NavComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val di = localDI()

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            val viewModel: HomeViewModel by di.instance()
            NavigationHandler(navController = navController, baseViewModel = viewModel)
            HomeScreen(viewModel = viewModel)
        }
        composable(route = AccountEditDestination.route) {
            val viewModel: AccountEditViewModel by di.instance()
            NavigationHandler(navController = navController, baseViewModel = viewModel)
            AccountCreationScreen(viewModel = viewModel)
        }
        composable(route = AddIncomeDestination.route) {
            val accountId = it.arguments?.getString(AddIncomeDestination.ACCOUNT_ID_ROUTE_ARG)

            val viewModel: AddIncomeViewModel by di.instance()

            viewModel.applyNavigationArguments(accountId?.toLongOrNull())

            NavigationHandler(navController = navController, baseViewModel = viewModel)
            AddIncomeScreen(viewModel = viewModel)
        }
        composable(route = AddExpenseDestination.route) {
            val accountId = it.arguments?.getString(AddIncomeDestination.ACCOUNT_ID_ROUTE_ARG)

            val viewModel: AddExpenseViewModel by di.instance()

            viewModel.applyNavigationArguments(accountId?.toLong())

            NavigationHandler(navController = navController, baseViewModel = viewModel)
            AddExpenseScreen(viewModel = viewModel)
        }
        composable(route = AccountOperationCategoryEditDestination.route) {
            val viewModel: AccountOperationCategoryEditViewModel by di.instance()
            NavigationHandler(navController = navController, baseViewModel = viewModel)
            AccountOperationCategoryEditScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun NavigationHandler(navController: NavHostController, baseViewModel: BaseViewModel) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        appEffects {
            handle<NavigateToEffect> {
                navController.navigate(it.route)
            }
            handle<NavigateBackToEffect> {
                navController.popBackStack(
                    it.destination,
                    it.inclusive,
                )
            }
            handle<NavigateBackEffect> {
                navController.popBackStack()
            }
            handle<NavigateUpEffect> {
                navController.navigateUp()
            }
        }.from(
            effectFlow = baseViewModel.effect,
            scope = coroutineScope
        )
    }
}