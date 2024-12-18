package by.varyvoda.android.moneymaster.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.ui.unit.dp
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
import by.varyvoda.android.moneymaster.ui.screen.account.category.CategoryEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.category.CategoryEditScreen
import by.varyvoda.android.moneymaster.ui.screen.account.category.CategoryEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditScreen
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.operation.edit.OperationEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.operation.edit.OperationEditScreen
import by.varyvoda.android.moneymaster.ui.screen.account.operation.edit.OperationEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.home.HomeDestination
import by.varyvoda.android.moneymaster.ui.screen.home.HomeScreen
import by.varyvoda.android.moneymaster.ui.screen.home.HomeViewModel
import org.kodein.di.DI
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val di = localDI()
    var bottomBarVisible by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { MainBottomBar(visible = bottomBarVisible, navController = navController) },
        contentWindowInsets = WindowInsets(0.dp),
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeDestination,
            modifier = modifier.padding(bottom = 60.dp),
        ) {
            route<HomeDestination, HomeViewModel>(di, navController) {
                bottomBarVisible = true
                HomeScreen(viewModel = it)
            }
            route<AccountEditDestination, AccountEditViewModel>(di, navController) {
                bottomBarVisible = false
                AccountEditScreen(viewModel = it)
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