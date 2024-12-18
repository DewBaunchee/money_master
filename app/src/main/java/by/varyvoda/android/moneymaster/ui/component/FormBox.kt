package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.ui.util.formPadding

@Composable
fun FormBox(
    buttons: List<@Composable ColumnScope.() -> Unit>,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(buttonSectionHeight: Dp) -> Unit,
) {
    val localDensity = LocalDensity.current
    var buttonColumnHeightDp by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        content(buttonColumnHeightDp)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .formPadding()
                .onGloballyPositioned {
                    buttonColumnHeightDp = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            for (button in buttons) {
                button()
            }
        }
    }
}
