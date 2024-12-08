package by.varyvoda.android.moneymaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import by.varyvoda.android.moneymaster.ui.effect.appEffects
import by.varyvoda.android.moneymaster.ui.effect.handle
import by.varyvoda.android.moneymaster.ui.screen.account.category.CategoryEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.category.CategoryEditScreen
import by.varyvoda.android.moneymaster.ui.screen.account.category.CategoryEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditScreen
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.operation.edit.EditOperationDestination
import by.varyvoda.android.moneymaster.ui.screen.account.operation.edit.EditOperationScreen
import by.varyvoda.android.moneymaster.ui.screen.account.operation.edit.EditOperationViewModel
import by.varyvoda.android.moneymaster.ui.screen.home.HomeDestination
import by.varyvoda.android.moneymaster.ui.screen.home.HomeScreen
import by.varyvoda.android.moneymaster.ui.screen.home.HomeViewModel
import org.kodein.di.DI
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
        startDestination = HomeDestination,
        modifier = modifier
    ) {
        route<HomeDestination, HomeViewModel>(di, navController) {
            HomeScreen(viewModel = it)
        }
        route<AccountEditDestination, AccountEditViewModel>(di, navController) {
            AccountEditScreen(viewModel = it)
        }
        route<EditOperationDestination, EditOperationViewModel>(di, navController) {
            EditOperationScreen(viewModel = it)
        }
        route<CategoryEditDestination, CategoryEditViewModel>(di, navController) {
            CategoryEditScreen(viewModel = it)
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
        viewModel.applyDestination(it.toRoute())
        NavigationHandler(navController = navController, baseViewModel = viewModel)
        screen(viewModel)
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