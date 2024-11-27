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
) {

    companion object {
        val Default = AccountTheme(name = "Default", gradientColors = listOf(Color.Black))

        val Gray = AccountTheme(
            name = "Gray",
            gradientColors = listOf(Color(0xFF202335), Color(0xFF5E669B))
        )
        val Blue = AccountTheme(
            name = "Blue",
            gradientColors = listOf(Color(0xFF0253F4), Color(0xFFA228FF))
        )
        val Purple = AccountTheme(
            name = "Purple",
            gradientColors = listOf(Color(0xFF6302F4), Color(0xFFA228FF))
        )
        val Pink = AccountTheme(
            name = "Pink",
            gradientColors = listOf(Color(0xFFF40202), Color(0xFFA228FF))
        )
        val Orange = AccountTheme(
            name = "Orange",
            gradientColors = listOf(Color(0xFFFF5E36), Color(0xFFFF282C))
        )

        val List = listOf(Gray, Blue, Purple, Pink, Orange)
    }
}
