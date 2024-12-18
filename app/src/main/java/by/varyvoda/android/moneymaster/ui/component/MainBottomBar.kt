package by.varyvoda.android.moneymaster.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.ui.screen.home.HomeDestination
import kotlin.reflect.KClass

fun NavController.isCurrent(kClass: KClass<out Any>) =
    currentDestination?.route.equals(kClass.qualifiedName)

@Composable
fun MainBottomBar(
    visible: Boolean,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    if (!visible) return

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
    ) {
        MainBottomBarButton(
            isSelected = navController.isCurrent(HomeDestination::class),
            onClick = { navController.navigate(HomeDestination) },
            iconRef = IconRef.Home,
            textId = R.string.home,
        )
        MainBottomBarButton(
            isSelected = false,
            onClick = {},
            iconRef = IconRef.Statistics,
            textId = R.string.statistics,
        )
        MainBottomBarButton(
            isSelected = false,
            onClick = {},
            iconRef = IconRef.History,
            textId = R.string.history,
        )
        MainBottomBarButton(
            isSelected = false,
            onClick = {},
            iconRef = IconRef.More,
            textId = R.string.more,
        )
    }
}

@Composable
fun RowScope.MainBottomBarButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    iconRef: IconRef,
    @StringRes textId: Int,
    modifier: Modifier = Modifier,
) {
    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            AppIcon(
                iconRef = iconRef,
            )
        },
        label = { Text(text = stringResource(textId)) },
        modifier = modifier,
        colors = NavigationBarItemDefaults.colors()
            .copy(selectedIndicatorColor = MaterialTheme.colorScheme.secondary),
    )
}