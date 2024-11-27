package by.varyvoda.android.moneymaster.data.model.account.mutation

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.varyvoda.android.moneymaster.config.database.ColorConverters
import by.varyvoda.android.moneymaster.data.model.domain.Id

@Entity(tableName = "account_mutation_category")
data class AccountMutationCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Id = 0,
    val name: String,
    val icon: Int,
    @TypeConverters(ColorConverters::class)
    val color: Color
) {
    companion object {
        val Unknown = AccountMutationCategory(
            name = "Unknown",
            icon = 0,
            color = Color.White
        )
    }
}