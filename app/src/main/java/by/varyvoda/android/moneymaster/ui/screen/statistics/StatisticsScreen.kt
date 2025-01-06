package by.varyvoda.android.moneymaster.ui.screen.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.details.account.operation.OperationDetails
import by.varyvoda.android.moneymaster.data.details.statistics.Statistics
import by.varyvoda.android.moneymaster.data.details.statistics.StatisticsItem
import by.varyvoda.android.moneymaster.data.details.statistics.StatisticsMap
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange
import by.varyvoda.android.moneymaster.data.model.domain.toDateRangeString
import by.varyvoda.android.moneymaster.ui.component.AppDateRangePickerIcon
import by.varyvoda.android.moneymaster.ui.component.CategoryBox
import by.varyvoda.android.moneymaster.ui.component.DateRangeSuggestions
import by.varyvoda.android.moneymaster.ui.component.IconAndText
import by.varyvoda.android.moneymaster.ui.component.ListPicker
import by.varyvoda.android.moneymaster.ui.component.ListPickerItemContainer
import by.varyvoda.android.moneymaster.ui.component.MoneyText
import by.varyvoda.android.moneymaster.ui.component.TitleAndText
import by.varyvoda.android.moneymaster.ui.component.TitleOperationTypeSelector
import by.varyvoda.android.moneymaster.ui.component.TitledList
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy
import java.util.Locale

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel, modifier: Modifier = Modifier) {
    val (operationType, dateRange) = viewModel.uiState.collectAsState().value
    val dateRangeSuggestions = viewModel.dateRangeSuggestions.collectAsState().value
    val statistics = viewModel.statistics.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        StatisticsTopBar(dateRange = dateRange)
        TitleOperationTypeSelector(
            options = listOf(
                Operation.Type.EXPENSE,
                Operation.Type.INCOME
            ),
            isSelected = { it == operationType },
            onSelect = { viewModel.changeOperationType(it) },
            modifier = Modifier
                .fillMaxWidth()
                .formPadding()
        )
        statistics?.let {
            MoneyText(
                currency = it.currency,
                amount = it.total,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Row(
            horizontalArrangement = Arrangement.formSpacedBy(Alignment.End),
            modifier = Modifier
                .fillMaxWidth()
                .formPadding()
        ) {
            DateRangeSuggestions(
                dateRangeSuggestions = dateRangeSuggestions,
                dateRange = dateRange,
                onDateRangeSelected = { viewModel.changeDateRange(it) },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            )
            AppDateRangePickerIcon(
                dateRange = dateRange,
                onDateRangeSelected = { viewModel.changeDateRange(it) },
                isSelected = dateRange != null && dateRangeSuggestions.none { dateRange == it.range },
                modifier = Modifier.wrapContentSize()
            )
        }
        CategoryStatistics(statistics = statistics)
    }
}

@Composable
fun StatisticsTopBar(dateRange: PrimitiveDateRange?, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .formPadding()
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.statistics),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = dateRange?.toDateRangeString(stringResource(R.string.pretty_date_format)) ?: "",
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun CategoryStatistics(
    statistics: Statistics?,
    modifier: Modifier = Modifier
) {
    TitledList(
        titleId = R.string.categories,
        onViewAllClick = {},
        modifier = modifier,
    ) {
        statistics?.let {
            CategoryStatisticsList(
                currency = it.currency,
                categoryStatisticsMap = it.categoryStatistics,
                isSelected = { false },
                onSelect = {},
            )
        }
    }
}

@Composable
fun CategoryStatisticsList(
    currency: Currency,
    categoryStatisticsMap: StatisticsMap<Category>,
    isSelected: (OperationDetails) -> Boolean,
    onSelect: (OperationDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListPicker(modifier = modifier) {
        items(categoryStatisticsMap.toList()) {
            CategoryStatisticsListItem(
                currency = currency,
                categoryStatistics = it,
            )
        }
    }
}

@Composable
fun CategoryStatisticsListItem(
    currency: Currency,
    categoryStatistics: StatisticsItem<Category>,
    modifier: Modifier = Modifier,
) {
    val category = categoryStatistics.first
    val amount = categoryStatistics.second.first
    val ratio = categoryStatistics.second.second
    ListPickerItemContainer(
        item = category,
        onClick = {},
        modifier = modifier,
    ) {
        IconAndText(
            icon = { CategoryBox(category = category, onlyIcon = true) }, text = {
                TitleAndText(
                    category.name,
                    ratio.asPercentString(),
                )
            },
            modifier = Modifier.weight(1f)
        )
        MoneyText(
            currency = currency,
            amount = amount,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

fun Float.asPercentString() =
    String.format(locale = Locale.getDefault(), "%.2f %%", this * 100)