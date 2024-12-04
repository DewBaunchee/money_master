package by.varyvoda.android.moneymaster.data.model.account

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.varyvoda.android.moneymaster.data.converter.ColorThemeConverters
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Money

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val initialBalance: Money,
    val currentBalance: Money,
    val currencyCode: String,
    @TypeConverters(ColorThemeConverters::class)
    val theme: ColorTheme,
)
