package by.varyvoda.android.moneymaster.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.service.icons.IconRefLoader

@ProvidedTypeConverter
class RoomIconConverter(
    private val loader: IconRefLoader
) {

    @TypeConverter
    fun iconToString(icon: IconRef) = icon.name

    @TypeConverter
    fun stringToIcon(name: String) = loader.load(name)!!
}