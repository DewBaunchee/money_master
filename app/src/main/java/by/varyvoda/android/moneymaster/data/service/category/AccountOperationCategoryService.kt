package by.varyvoda.android.moneymaster.data.service.category

import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

interface AccountOperationCategoryService {

    suspend fun createCategory(
        name: String,
        operationType: AccountOperation.Type,
        iconRef: IconRef,
        colorTheme: ColorTheme,
    )
}