package by.varyvoda.android.moneymaster.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import by.varyvoda.android.moneymaster.ui.navigation.NavComponent

@Composable
fun MoneyMasterApp(navController: NavHostController = rememberNavController()) {
    NavComponent(navController)
}