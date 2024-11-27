package by.varyvoda.android.moneymaster.config.database

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ColorConverters {

    @TypeConverter
    fun toLong(color: Color): Long = color.value.toLong()

    @TypeConverter
    fun toColor(long: Long): Color = Color(long)
}

class ColorListConverters {

    @TypeConverter
    fun toString(colors: List<Color>): String = Gson().toJson(colors.map { it.value })

    @TypeConverter
    fun toColorList(json: String): List<Color> =
        Gson().fromJson<List<Int>>(json, object : TypeToken<List<Int>>() {}.type)
            .map { Color(it) }
}