package by.varyvoda.android.moneymaster.data.model.account.operation

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.varyvoda.android.moneymaster.config.database.ColorListConverters
import by.varyvoda.android.moneymaster.data.model.domain.Id

@Entity(tableName = "account_operation_category")
data class AccountOperationCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Id = 0,
    val operationType: AccountOperation.Type,
    val name: String,
    val icon: Int,
    @TypeConverters(ColorListConverters::class)
    val gradientColors: List<Color>
)