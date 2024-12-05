package by.varyvoda.android.moneymaster.data.converter

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

fun colorToJson(color: Color): String = Gson().toJson(color.value)
fun jsonToColor(json: String) = Color(Gson().fromJson(json, ULong::class.java))

fun colorListToJson(colors: List<Color>): String = Gson().toJson(colors.map { it.value })
fun jsonToColorList(json: String) =
    Gson().fromJson<List<ULong>>(json, object : TypeToken<List<ULong>>() {}.type)
        .map { Color(it) }

fun colorThemeToJson(theme: ColorTheme): String = JsonObject().let { jsonObject ->
    jsonObject.addProperty("name", theme.name)
    jsonObject.addProperty("colors", colorListToJson(theme.colors))
    jsonObject.toString()
}

fun jsonToColorTheme(json: String) =
    Gson().fromJson(json, JsonObject::class.java).let { jsonObject ->
        ColorTheme(
            name = jsonObject.getAsJsonPrimitive("name").asString,
            colors = jsonToColorList(jsonObject.getAsJsonPrimitive("colors").asString)
        )
    }


class ColorConverters {

    @TypeConverter
    fun toString(color: Color) = colorToJson(color)

    @TypeConverter
    fun toColor(json: String) = jsonToColor(json)
}

class ColorListConverters {

    @TypeConverter
    fun toString(colors: List<Color>) = colorListToJson(colors)

    @TypeConverter
    fun toColorList(json: String) = jsonToColorList(json)
}

class ColorThemeConverters {

    @TypeConverter
    fun toString(theme: ColorTheme) = colorThemeToJson(theme)

    @TypeConverter
    fun toColorList(json: String) = jsonToColorTheme(json)
}