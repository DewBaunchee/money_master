package by.varyvoda.android.moneymaster.data.service.category

import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperationCategory
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.AccountOperationCategoryRepository

class AccountOperationCategoryServiceImpl(
    private val accountOperationCategoryRepository: AccountOperationCategoryRepository,
) : AccountOperationCategoryService {

    override suspend fun createCategory(
        name: String,
        operationType: AccountOperation.Type,
        icon: Int,
        gradientColors: List<Color>
    ) {
        accountOperationCategoryRepository.insert(
            AccountOperationCategory(
                name = name,
                operationType = operationType,
                icon = icon,
                gradientColors = gradientColors,
            )
        )
    }
}