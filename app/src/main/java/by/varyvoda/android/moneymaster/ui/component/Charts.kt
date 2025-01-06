package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@Composable
fun DonutChart(
    data: PieChartData,
    pieChartConfig: PieChartConfig =
        PieChartConfig(
            isAnimationEnable = true,
        ),
    modifier: Modifier = Modifier,
) {
    DonutPieChart(
        pieChartData = data,
        pieChartConfig = pieChartConfig,
        modifier = modifier,
    )
}