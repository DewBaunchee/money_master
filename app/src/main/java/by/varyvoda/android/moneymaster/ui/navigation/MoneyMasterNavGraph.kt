package by.varyvoda.android.moneymaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import by.varyvoda.android.moneymaster.ui.AppViewModel
import by.varyvoda.android.moneymaster.ui.account.addincome.AddIncomeDestination
import by.varyvoda.android.moneymaster.ui.account.addincome.AddIncomeScreen
import by.varyvoda.android.moneymaster.ui.account.creation.AccountCreationDestination
import by.varyvoda.android.moneymaster.ui.account.creation.AccountCreationScreen
import by.varyvoda.android.moneymaster.ui.home.HomeDestination
import by.varyvoda.android.moneymaster.ui.home.HomeScreen

@Composable
fun MoneyMasterNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val appViewModel = viewModel<AppViewModel>()

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                appViewModel = appViewModel,
                navigateTo = { route -> navController.navigate(route) }
            )
        }
        composable(route = AccountCreationDestination.route) {
            AccountCreationScreen(
                appViewModel = appViewModel,
                navigateUp = { navController.navigateUp() }
            )
        }
        composable(route = AddIncomeDestination.route) {
            AddIncomeScreen(
                appViewModel = appViewModel,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}
