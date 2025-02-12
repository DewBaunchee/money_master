package by.varyvoda.android.moneymaster.data.model.account.operation

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.varyvoda.android.moneymaster.data.converter.ColorThemeConverters
import by.varyvoda.android.moneymaster.data.converter.RoomIconConverter
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Id = 0,
    val operationType: Operation.Type,
    val name: String,
    @TypeConverters(RoomIconConverter::class)
    val iconRef: IconRef,
    @TypeConverters(ColorThemeConverters::class)
    val colorTheme: ColorTheme,
)