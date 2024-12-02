package by.varyvoda.android.moneymaster.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex

@Composable
fun FullScreenDialog(
    @StringRes titleId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Dialog(
        onDismissRequest = onBackClick,
        DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Scaffold(
            topBar = {
                MainTopBar(
                    titleId = titleId,
                    onBackClick = onBackClick
                )
            },
            modifier = modifier
                .fillMaxSize()
                .zIndex(10F),
            content = content,
        )
    }
}