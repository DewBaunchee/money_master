package by.varyvoda.android.moneymaster.ui.screen.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.details.account.operation.OperationDetails
import by.varyvoda.android.moneymaster.data.details.statistics.Statistics
import by.varyvoda.android.moneymaster.data.details.statistics.StatisticsItem
import by.varyvoda.android.moneymaster.data.details.statistics.StatisticsList
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange
import by.varyvoda.android.moneymaster.data.model.domain.toDateRangeString
import by.varyvoda.android.moneymaster.ui.component.AppDateRangePickerIcon
import by.varyvoda.android.moneymaster.ui.component.CategoryBox
import by.varyvoda.android.moneymaster.ui.component.DateRangeSuggestions
import by.varyvoda.android.moneymaster.ui.component.DonutChart
import by.varyvoda.android.moneymaster.ui.component.IconAndText
import by.varyvoda.android.moneymaster.ui.component.ListPicker
import by.varyvoda.android.moneymaster.ui.component.ListPickerItemContainer
import by.varyvoda.android.moneymaster.ui.component.MoneyText
import by.varyvoda.android.moneymaster.ui.component.TitleAndText
import by.varyvoda.android.moneymaster.ui.component.TitleOperationTypeSelector
import by.varyvoda.android.moneymaster.ui.component.TitledList
import by.varyvoda.android.moneymaster.ui.util.collectValue
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy
import by.varyvoda.android.moneymaster.ui.util.ofScreenHeight
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import java.util.Locale

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel, modifier: Modifier = Modifier) {
    val (operationType, dateRange) = viewModel.uiState.collectValue()
    val dateRangeSuggestions = viewModel.dateRangeSuggestions.collectValue()
    val statistics = viewModel.statistics.collectValue()

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
        if (statistics != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ofScreenHeight(0.3f))
            ) {
                if (statistics.categoryStatistics.isNotEmpty()) {
                    DonutChart(
                        data = PieChartData(
                            slices = statistics.categoryStatistics.toList()
                                .map {
                                    PieChartData.Slice(
                                        label = it.first.name,
                                        value = it.second.ratio,
                                        color = it.first.colorTheme.colors.first(),
                                    )
                                },
                            plotType = PlotType.Donut,
                        ),
                        modifier = Modifier.formPadding()
                    )
                }
                MoneyText(
                    currency = statistics.currency,
                    amount = statistics.total,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
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
                categoryStatisticsList = it.categoryStatistics,
                isSelected = { false },
                onSelect = {},
            )
        }
    }
}

@Composable
fun CategoryStatisticsList(
    currency: Currency,
    categoryStatisticsList: StatisticsList<Category>,
    isSelected: (OperationDetails) -> Boolean,
    onSelect: (OperationDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListPicker(modifier = modifier) {
        items(categoryStatisticsList.size) { index ->
            var totalRatioBefore = 0f
            for (i in 0 until index) {
                totalRatioBefore += categoryStatisticsList[i].second.ratio
            }

            CategoryStatisticsListItem(
                currency = currency,
                totalRatioBefore = totalRatioBefore,
                categoryStatistics = categoryStatisticsList[index],
            )
        }
    }
}

@Composable
fun CategoryStatisticsListItem(
    currency: Currency,
    totalRatioBefore: Float,
    categoryStatistics: StatisticsItem<Category>,
    modifier: Modifier = Modifier,
) {
    val category = categoryStatistics.first
    val amount = categoryStatistics.second.amount
    val ratio = categoryStatistics.second.ratio
    ListPickerItemContainer(
        item = category,
        onClick = {},
        modifier = modifier,
    ) {
        DonutChart(
            data = PieChartData(
                slices = categoryStatistics.let {
                    listOf(
                        PieChartData.Slice(
                            label = "",
                            value = totalRatioBefore,
                            color = Color(0x00000000),
                        ),
                        PieChartData.Slice(
                            label = it.first.name,
                            value = it.second.ratio,
                            color = it.first.colorTheme.colors.first(),
                        ),
                        PieChartData.Slice(
                            label = "",
                            value = 1 - totalRatioBefore - it.second.ratio,
                            color = Color(0x00000000),
                        ),
                    )
                },
                plotType = PlotType.Donut,
            ),
            pieChartConfig = PieChartConfig(
                isAnimationEnable = true,
                strokeWidth = 20f,
            ),
            modifier = Modifier
                .size(48.dp)
                .padding(dimensionResource(R.dimen.small_chart_padding))
        )
        IconAndText(
            icon = {
                CategoryBox(category = category, onlyIcon = true)
            },
            text = {
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