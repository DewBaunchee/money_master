package by.varyvoda.android.moneymaster.ui.screen.more

import androidx.annotation.StringRes
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

@Serializable
object MoreDestination

data class MoreScreenOptionModel(
    val iconRef: IconRef,
    @StringRes val labelId: Int,
    private val action: () -> Unit
) {
    fun onClick() = action()
}

class MoreViewModel : BaseViewModel<MoreDestination>() {

    private val _uiState = MutableStateFlow(MoreUiState(
        options = listOf(
            MoreScreenOptionModel(
                iconRef = IconRef.CreateAccount,
                labelId = R.string.create_account,
            ) { navigateTo(AccountEditDestination()) }
        )
    ))
    val uiState = _uiState.asStateFlow()
}

data class MoreUiState(
    val options: List<MoreScreenOptionModel> = listOf()
)