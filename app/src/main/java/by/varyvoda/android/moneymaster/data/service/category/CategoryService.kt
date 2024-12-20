package by.varyvoda.android.moneymaster.data.service.category

import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

interface CategoryService {

    suspend fun createCategory(
        name: String,
        operationType: Operation.Type,
        iconRef: IconRef,
        colorTheme: ColorTheme,
    )

    suspend fun updateCategory(
        categoryId: Id,
        name: String,
        operationType: Operation.Type,
        iconRef: IconRef,
        colorTheme: ColorTheme,
    )
}