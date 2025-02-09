package by.varyvoda.android.moneymaster.ui.util

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun smallPadding() = dimensionResource(R.dimen.small_padding)

@Composable
fun formPadding() = dimensionResource(R.dimen.form_padding)

@Composable
fun formSpacedBy() = dimensionResource(R.dimen.form_spaced_by)


@Composable
fun Modifier.smallPadding() = padding(by.varyvoda.android.moneymaster.ui.util.smallPadding())

@Composable
fun Modifier.formPadding() = padding(by.varyvoda.android.moneymaster.ui.util.formPadding())

@Composable
fun Arrangement.formSpacedBy() =
    spacedBy(by.varyvoda.android.moneymaster.ui.util.formSpacedBy())

@Composable
fun Arrangement.formSpacedBy(alignment: Alignment.Vertical = Alignment.Top) =
    spacedBy(by.varyvoda.android.moneymaster.ui.util.formSpacedBy(), alignment)

@Composable
fun Arrangement.formSpacedBy(alignment: Alignment.Horizontal = Alignment.Start) =
    spacedBy(by.varyvoda.android.moneymaster.ui.util.formSpacedBy(), alignment)

@Composable
fun ofScreenHeight(amount: Float) =
    (LocalConfiguration.current.screenHeightDp * amount).dp

operator fun PaddingValues.plus(other: PaddingValues): PaddingValues = PaddingValues(
    start = this.calculateStartPadding(LayoutDirection.Ltr) +
            other.calculateStartPadding(LayoutDirection.Ltr),
    top = this.calculateTopPadding() + other.calculateTopPadding(),
    end = this.calculateEndPadding(LayoutDirection.Ltr) +
            other.calculateEndPadding(LayoutDirection.Ltr),
    bottom = this.calculateBottomPadding() + other.calculateBottomPadding(),
)

@Composable
fun Modifier.debugBorder() = border(
    1.dp,
    listOf(Color.Red, Color.Green, Color.Blue, Color.Magenta).random()
)

@Composable
fun <T> Flow<T>.collectValue(initial: T) = collectAsState(initial).value

@Composable
fun <T> StateFlow<T>.collectValue() = collectAsState().value