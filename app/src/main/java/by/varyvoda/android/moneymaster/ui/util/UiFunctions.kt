package by.varyvoda.android.moneymaster.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.LayoutDirection
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.currency.Currency

@Composable
fun formPadding() = dimensionResource(R.dimen.form_padding)

@Composable
fun formSpacedBy() = dimensionResource(R.dimen.form_spaced_by)


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

fun makeMoneyString(money: String, currency: Currency?): String {
    return if (currency == null) money else currency.symbol + money
}

operator fun PaddingValues.plus(other: PaddingValues): PaddingValues = PaddingValues(
    start = this.calculateStartPadding(LayoutDirection.Ltr) +
            other.calculateStartPadding(LayoutDirection.Ltr),
    top = this.calculateTopPadding() + other.calculateTopPadding(),
    end = this.calculateEndPadding(LayoutDirection.Ltr) +
            other.calculateEndPadding(LayoutDirection.Ltr),
    bottom = this.calculateBottomPadding() + other.calculateBottomPadding(),
)