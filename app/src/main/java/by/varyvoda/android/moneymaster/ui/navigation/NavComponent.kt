package by.varyvoda.android.moneymaster.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import by.varyvoda.android.moneymaster.config.appModule
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
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseDestination
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseScreen
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountCreationDestination
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountCreationScreen
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountCreationViewModel
import by.varyvoda.android.moneymaster.ui.screen.home.HomeDestination
import by.varyvoda.android.moneymaster.ui.screen.home.HomeScreen
import by.varyvoda.android.moneymaster.ui.screen.home.HomeViewModel
import org.kodein.di.DI
import org.kodein.di.DI.Companion.invoke
import org.kodein.di.compose.localDI
import org.kodein.di.compose.withDI
import org.kodein.di.instance

@Composable
fun NavComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) = withDI(DI {
    import(appModule(context))
}) {
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
        composable(route = AccountCreationDestination.route) {
            val viewModel: AccountCreationViewModel by di.instance()
            NavigationHandler(navController = navController, baseViewModel = viewModel)
            AccountCreationScreen(viewModel = viewModel)
        }
        composable(route = AddIncomeDestination.route) {
            val viewModel: AddIncomeViewModel by di.instance()
            NavigationHandler(navController = navController, baseViewModel = viewModel)
            AddIncomeScreen(viewModel = viewModel)
        }
        composable(route = AddExpenseDestination.route) {
            val viewModel: AddExpenseViewModel by di.instance()
            NavigationHandler(navController = navController, baseViewModel = viewModel)
            AddExpenseScreen(viewModel = viewModel)
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