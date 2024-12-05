package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.ui.util.formPadding

@Composable
fun FormBox(
    buttons: List<@Composable ColumnScope.() -> Unit>,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        content()
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .formPadding()
        ) {
            for (button in buttons) {
                button()
            }
        }
    }
}
