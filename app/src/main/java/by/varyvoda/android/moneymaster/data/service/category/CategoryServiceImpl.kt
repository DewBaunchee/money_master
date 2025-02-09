package by.varyvoda.android.moneymaster.data.service.category

import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.CategoryRepository

class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {

    override suspend fun createOrUpdateCategory(
        id: Id,
        name: String,
        operationType: Operation.Type,
        iconRef: IconRef,
        colorTheme: ColorTheme
    ) {
        categoryRepository.upsert(
            Category(
                id = id,
                name = name,
                operationType = operationType,
                iconRef = iconRef,
                colorTheme = colorTheme,
            )
        )
    }

    override suspend fun deleteCategory(id: Id) {
        categoryRepository.deleteById(id)
    }
}