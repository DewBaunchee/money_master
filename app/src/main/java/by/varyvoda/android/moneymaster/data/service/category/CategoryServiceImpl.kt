package by.varyvoda.android.moneymaster.data.service.category

import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.CategoryRepository

class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {

    override suspend fun createCategory(
        name: String,
        operationType: Operation.Type,
        iconRef: IconRef,
        colorTheme: ColorTheme
    ) {
        categoryRepository.insert(
            Category(
                name = name,
                operationType = operationType,
                iconRef = iconRef,
                colorTheme = colorTheme,
            )
        )
    }
}