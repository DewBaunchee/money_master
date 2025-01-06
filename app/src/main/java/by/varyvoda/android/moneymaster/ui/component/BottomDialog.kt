package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.ofScreenHeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(),
        modifier = modifier,
    ) {
        Column(
            modifier = modifier
//                .nestedScroll(rememberNestedScrollInteropConnection())
                .heightIn(min = ofScreenHeight(0.3f), max = ofScreenHeight(0.5f)),
            content = content,
        )
    }
}

@Composable
fun TitledSearchableBottomDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    searchEnabled: Boolean = false,
    searchString: String = "",
    onSearchStringChange: (String) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    BottomDialog(onDismissRequest, modifier = modifier) {
        TitledContent(
            applyFormPadding = false,
            verticalArrangement = Arrangement.Top,
            title = { AppTitle(R.string.currency, modifier = Modifier.formPadding()) },
        ) {
            SearchableContent(
                searchEnabled = searchEnabled,
                searchString = searchString,
                onSearchStringChange = onSearchStringChange,
            ) {
                content()
            }
        }
    }
}