package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchableContent(
    modifier: Modifier = Modifier,
    searchEnabled: Boolean = false,
    searchString: String = "",
    onSearchStringChange: (String) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier) {
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