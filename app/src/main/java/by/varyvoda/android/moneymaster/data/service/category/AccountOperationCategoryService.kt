package by.varyvoda.android.moneymaster.data.service.category

import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperation

interface AccountOperationCategoryService {

    suspend fun createCategory(
        name: String,
        operationType: AccountOperation.Type,
        icon: Int,
        gradientColors: List<Color>
    )
}