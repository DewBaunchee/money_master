package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun TitledContent(
    applyFormPadding: Boolean = true,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical =
        if (applyFormPadding)
            Arrangement.formSpacedBy()
        else
            Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    title: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = if (applyFormPadding) modifier.formPadding() else modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        title()
        content()
    }
}