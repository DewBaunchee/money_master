package by.varyvoda.android.moneymaster.data.service.icons

import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import kotlinx.coroutines.flow.Flow

typealias IconRefMap = Map<String, List<IconRef>>

interface IconsService : IconRefLoader {

    fun getAllIconRefs(): Flow<List<IconRef>>

    fun getAllCategoriesToIconRefs(): Flow<IconRefMap>

    fun getAllIconRefsByCategory(category: String): Flow<List<IconRef>>

    fun getIconRefByName(name: String): Flow<IconRef>

    fun getIconRefsBySearchString(searchString: String): Flow<IconRefMap>

}