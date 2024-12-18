package by.varyvoda.android.moneymaster.data.model.icon

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.SettingsInputSvideo
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

data class IconRef(
    val name: String,
    val label: String,
    val category: String,
    private val loader: suspend () -> ImageVector?,
) {

    companion object {

        private const val APPLICATION_CATEGORY = "Application"

        fun ImageVector.toIconRef(label: String = name) = IconRef(
            name = name,
            label = label,
            category = APPLICATION_CATEGORY,
            loader = { this },
        )

        val DefaultVector = Icons.Filled.SettingsInputSvideo
        val Default = IconRef(
            name = "Default",
            label = "Default",
            category = APPLICATION_CATEGORY,
            loader = { DefaultVector }
        )

        val Back = Icons.AutoMirrored.Filled.ArrowBack.toIconRef("Back")
        val Home = Icons.Filled.Home.toIconRef("Home")
        val Selected = Icons.Filled.Done.toIconRef("Done")
        val DatePicker = Icons.Filled.DateRange.toIconRef("Select date")
        val Profile = Icons.Filled.Person.toIconRef("Profile icon")
        val Notifications = Icons.Filled.Notifications.toIconRef("Notifications")
        val AddIncome = Icons.Filled.KeyboardArrowUp.toIconRef("Add income")
        val AddExpense = Icons.Filled.KeyboardArrowDown.toIconRef("Add expense")
        val Statistics = Icons.Filled.QueryStats.toIconRef("Statistics")
        val History = Icons.Filled.History.toIconRef("History")
        val More = Icons.Filled.MoreHoriz.toIconRef("More")
    }

    suspend fun load() = loader()

    @Composable
    fun labeledBy(@StringRes labelId: Int) = copy(label = stringResource(labelId))
}