package by.varyvoda.android.moneymaster.ui.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.icon.IconRef.Companion.toIconRef

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    @StringRes titleId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(titleId)) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                AppIcon(
                    iconRef = Icons.AutoMirrored.Filled.ArrowBack.toIconRef(stringResource(R.string.back)),
                )
            }
        },
        modifier = modifier
    )
}