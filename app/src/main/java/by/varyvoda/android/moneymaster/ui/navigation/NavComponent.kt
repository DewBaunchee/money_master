package by.varyvoda.android.moneymaster.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.base.NavigateBackEffect
import by.varyvoda.android.moneymaster.ui.base.NavigateBackToEffect
import by.varyvoda.android.moneymaster.ui.base.NavigateToEffect
import by.varyvoda.android.moneymaster.ui.base.NavigateUpEffect
import by.varyvoda.android.moneymaster.ui.component.MainBottomBar
import by.varyvoda.android.moneymaster.ui.effect.appEffects
import by.varyvoda.android.moneymaster.ui.effect.handle
import by.varyvoda.android.moneymaster.ui.screen.account.AccountsDestination
import by.varyvoda.android.moneymaster.ui.screen.account.AccountsScreen
import by.varyvoda.android.moneymaster.ui.screen.account.AccountsViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditScreen
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.category.CategoriesDestination
import by.varyvoda.android.moneymaster.ui.screen.category.CategoriesScreen
import by.varyvoda.android.moneymaster.ui.screen.category.CategoriesViewModel
import by.varyvoda.android.moneymaster.ui.screen.category.edit.CategoryEditDestination
import by.varyvoda.android.moneymaster.ui.screen.category.edit.CategoryEditScreen
import by.varyvoda.android.moneymaster.ui.screen.category.edit.CategoryEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.currency.CurrenciesDestination
import by.varyvoda.android.moneymaster.ui.screen.currency.CurrenciesScreen
import by.varyvoda.android.moneymaster.ui.screen.currency.CurrenciesViewModel
import by.varyvoda.android.moneymaster.ui.screen.home.HomeDestination
import by.varyvoda.android.moneymaster.ui.screen.home.HomeScreen
import by.varyvoda.android.moneymaster.ui.screen.home.HomeViewModel
import by.varyvoda.android.moneymaster.ui.screen.more.MoreDestination
import by.varyvoda.android.moneymaster.ui.screen.more.MoreScreen
import by.varyvoda.android.moneymaster.ui.screen.more.MoreViewModel
import by.varyvoda.android.moneymaster.ui.screen.operation.edit.OperationEditDestination
import by.varyvoda.android.moneymaster.ui.screen.operation.edit.OperationEditScreen
import by.varyvoda.android.moneymaster.ui.screen.operation.edit.OperationEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.statistics.StatisticsDestination
import by.varyvoda.android.moneymaster.ui.screen.statistics.StatisticsScreen
import by.varyvoda.android.moneymaster.ui.screen.statistics.StatisticsViewModel
import by.varyvoda.android.moneymaster.ui.util.collectValue
import org.kodein.di.DI
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun NavComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val di = localDI()
    var bottomBarVisible by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { MainBottomBar(visible = bottomBarVisible, navController = navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination,
            modifier = modifier.padding(innerPadding),
        ) {
            route<HomeDestination, HomeViewModel>(di, navController) {
                bottomBarVisible = true
                HomeScreen(viewModel = it)
            }
            route<StatisticsDestination, StatisticsViewModel>(di, navController) {
                bottomBarVisible = true
                StatisticsScreen(viewModel = it)
            }
            route<MoreDestination, MoreViewModel>(di, navController) {
                bottomBarVisible = true
                MoreScreen(viewModel = it)
            }

            route<AccountsDestination, AccountsViewModel>(di, navController) {
                bottomBarVisible = false
                AccountsScreen(viewModel = it)
            }
            route<AccountEditDestination, AccountEditViewModel>(di, navController) {
                bottomBarVisible = false
                AccountEditScreen(viewModel = it)
            }
            route<CurrenciesDestination, CurrenciesViewModel>(di, navController) {
                bottomBarVisible = false
                CurrenciesScreen(viewModel = it)
            }
            route<CategoriesDestination, CategoriesViewModel>(di, navController) {
                bottomBarVisible = false
                CategoriesScreen(viewModel = it)
            }
            route<OperationEditDestination, OperationEditViewModel>(di, navController) {
                bottomBarVisible = false
                OperationEditScreen(viewModel = it)
            }
            route<CategoryEditDestination, CategoryEditViewModel>(di, navController) {
                bottomBarVisible = false
                CategoryEditScreen(viewModel = it)
            }
        }
    }
}

private inline fun <reified D : Any, reified VM : BaseViewModel<D>> NavGraphBuilder.route(
    di: DI,
    navController: NavHostController,
    crossinline screen: @Composable (VM) -> Unit
) {
    composable<D> {
        val viewModel: VM by di.instance()
        val route = it.toRoute<D>()
        
        LaunchedEffect(route) {
            viewModel.applyDestination(route)
        }

        if (viewModel.initialized.collectValue()) {
            NavigationHandler(navController = navController, baseViewModel = viewModel)
            screen(viewModel)
        }
    }
}

@Composable
fun <D> NavigationHandler(navController: NavHostController, baseViewModel: BaseViewModel<D>) {
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