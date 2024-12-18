package by.varyvoda.android.moneymaster.data.converter

import androidx.room.TypeConverter
import java.util.UUID

class UUIDConverters {

    @TypeConverter
    fun toString(uuid: UUID) = uuid.toString()

    @TypeConverter
    fun fromString(string: String): UUID = UUID.fromString(string)
}