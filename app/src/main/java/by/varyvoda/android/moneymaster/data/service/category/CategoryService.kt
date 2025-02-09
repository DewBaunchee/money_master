package by.varyvoda.android.moneymaster.data.service.category

import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.DEFAULT_ID
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

interface CategoryService {

    suspend fun createOrUpdateCategory(
        id: Id = DEFAULT_ID,
        name: String,
        operationType: Operation.Type,
        iconRef: IconRef,
        colorTheme: ColorTheme,
    )

    suspend fun deleteCategory(id: Id)
}