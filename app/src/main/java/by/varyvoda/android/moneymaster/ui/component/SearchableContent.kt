package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun SearchableContent(
    searchEnabled: Boolean = false,
    searchString: String = "",
    onSearchStringChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(verticalArrangement = Arrangement.formSpacedBy(), modifier = modifier) {
        if (searchEnabled) {
            SearchField(
                searchString = searchString,
                onSearchStringChange = onSearchStringChange,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        content()
    }
}