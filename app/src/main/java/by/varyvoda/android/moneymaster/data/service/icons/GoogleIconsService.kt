package by.varyvoda.android.moneymaster.data.service.icons

import android.content.res.AssetManager
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.InputStreamReader

private const val ICONS_NAMES_FILENAME = "google_icons_names.json"

class GoogleIconsService(assets: AssetManager) : IconsService {

    private val categoryToIcons = assets.open(ICONS_NAMES_FILENAME).let { fileStream ->
        val categories = Gson().fromJson(InputStreamReader(fileStream), JsonObject::class.java)
        buildMap {
            categories.keySet().forEach { category ->
                put(
                    category,
                    categories.getAsJsonArray(category).map {
                        val nameAndLabel = it.asJsonArray
                        val name = nameAndLabel.get(0).asString
                        val label = nameAndLabel.get(1).asString
                        IconRef(
                            name = name,
                            category = category,
                            label = label,
                            loader = getLoaderFor(name)
                        )
                    }
                )
            }
        }
    }
    private val iconList = categoryToIcons.flatMap { (_, value) -> value }

    override fun getAllIconRefs(): Flow<List<IconRef>> = flow { emit(iconList) }

    override fun getAllCategoriesToIconRefs(): Flow<IconRefMap> = flow { emit(categoryToIcons) }

    override fun getAllIconRefsByCategory(category: String): Flow<List<IconRef>> =
        flow {
            emit(
                requireNotNull(categoryToIcons[category]) { "Can't find icons with category: $category" }
            )
        }

    override fun getIconRefByName(name: String): Flow<IconRef> =
        flow { emit(load(name)) }

    override fun getIconRefsBySearchString(searchString: String): Flow<IconRefMap> {
        val wholeMap = getAllCategoriesToIconRefs()

        if (searchString.isBlank()) return wholeMap

        return wholeMap
            .map { iconRefMap ->
                iconRefMap.mapValues { (_, iconRefs) ->
                    iconRefs.filter {
                        it.label.contains(
                            searchString,
                            ignoreCase = true,
                        )
                    }
                }
            }
    }

    override fun load(name: String) =
        requireNotNull(iconList.find { it.name == name }) { "Can't find icon with name $name" }
}

private fun getLoaderFor(name: String): () -> ImageVector? = loader@{
    try {
        val className = "androidx.compose.material.icons.filled.${name}Kt"
        val cl = Class.forName(className)
        val method = cl.declaredMethods.first()
        return@loader method.invoke(null, Icons.Filled) as ImageVector
    } catch (e: Throwable) {
        return@loader null
    }
}