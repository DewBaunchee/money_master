package by.varyvoda.android.moneymaster.data.service.category

import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperationCategory
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.AccountOperationCategoryRepository

class AccountOperationCategoryServiceImpl(
    private val accountOperationCategoryRepository: AccountOperationCategoryRepository,
) : AccountOperationCategoryService {

    override suspend fun createCategory(
        name: String,
        operationType: AccountOperation.Type,
        iconRef: IconRef,
        colorTheme: ColorTheme
    ) {
        accountOperationCategoryRepository.insert(
            AccountOperationCategory(
                name = name,
                operationType = operationType,
                iconRef = iconRef,
                colorTheme = colorTheme,
            )
        )
    }
}