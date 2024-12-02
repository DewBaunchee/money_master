package by.varyvoda.android.moneymaster.data.model.account.theme

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.varyvoda.android.moneymaster.config.database.ColorListConverters
import by.varyvoda.android.moneymaster.data.model.domain.Id

@Entity(tableName = "account_theme")
data class AccountTheme(
    @PrimaryKey(autoGenerate = true)
    val id: Id = 0,
    val name: String,
    @TypeConverters(ColorListConverters::class)
    val gradientColors: List<Color>
)
