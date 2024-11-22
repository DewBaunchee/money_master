package by.varyvoda.android.moneymaster.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import by.varyvoda.android.moneymaster.ui.navigation.MoneyMasterNavGraph

@Composable
fun MoneyMasterApp(navController: NavHostController = rememberNavController()) {
    MoneyMasterNavGraph(navController)
}