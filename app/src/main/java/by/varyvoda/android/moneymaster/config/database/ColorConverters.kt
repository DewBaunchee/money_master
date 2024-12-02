package by.varyvoda.android.moneymaster.config.database

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ColorConverters {

    @TypeConverter
    fun toString(color: Color): String = Gson().toJson(color.value)

    @TypeConverter
    fun toColor(json: String): Color =
        Color(Gson().fromJson<ULong>(json, ULong::class.java))
}

class ColorListConverters {

    @TypeConverter
    fun toString(colors: List<Color>): String = Gson().toJson(colors.map { it.value })

    @TypeConverter
    fun toColorList(json: String): List<Color> =
        Gson().fromJson<List<ULong>>(json, object : TypeToken<List<ULong>>() {}.type)
            .map { Color(it) }
}