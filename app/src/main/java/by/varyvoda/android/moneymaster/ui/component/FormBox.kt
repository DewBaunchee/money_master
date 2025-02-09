package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.ui.theme.Negative
import by.varyvoda.android.moneymaster.ui.util.formPadding

@Composable
fun FormBox(
    buttons: List<@Composable ColumnScope.() -> Unit>,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(buttonSectionHeightDp: Dp) -> Unit,
) {
    val localDensity = LocalDensity.current
    var bottomSectionHeightDp by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        content(bottomSectionHeightDp)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .formPadding()
                .onGloballyPositioned {
                    bottomSectionHeightDp = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            for (button in buttons) {
                button()
            }
        }
    }
}

interface SavableViewModel {

    fun canSave(): Boolean

    fun save()
}

@Composable
fun SaveButton(viewModel: SavableViewModel, modifier: Modifier = Modifier) {
    AppButton(
        onClick = { viewModel.save() },
        enabled = viewModel.canSave(),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.save))
    }
}


interface DeletableViewModel {

    fun canDelete(): Boolean

    fun delete()
}

@Composable
fun DeleteButton(viewModel: DeletableViewModel, modifier: Modifier = Modifier) {
    AppButton(
        onClick = { viewModel.delete() },
        isSecondary = true,
        enabled = viewModel.canDelete(),
        modifier = modifier
            .fillMaxWidth()
    ) {
        AppIcon(
            iconRef = IconRef.Delete,
            tint = Negative
        )
        Text(
            text = stringResource(R.string.delete),
            color = Negative
        )
    }
}
