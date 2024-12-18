package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun AccountGallery(
    accounts: List<AccountDetails>,
    currentAccount: AccountDetails,
    onSelect: (AccountDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState { accounts.size }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    accounts.indexOf(currentAccount).let {
        if (it == -1) throw IllegalArgumentException("Cannot find current account")
        if (it != pagerState.currentPage && !pagerState.isScrollInProgress) {
            LaunchedEffect(pagerState) {
                pagerState.animateScrollToPage(it)
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onSelect(accounts[page])
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier,
            contentPadding = PaddingValues(formPadding()),
            pageSpacing = formSpacedBy(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            with(accounts[it]) {
                AccountCard(
                    iconRef = model.iconRef,
                    theme = model.theme,
                    title = model.name,
                    balance = model.currentBalance.toString(),
                    currency = currency.collectAsState(null).value,
                )
            }
        }
        DotsIndicator(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            dotCount = accounts.size,
            selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
            dotSize = 8.dp
        )
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    dotCount: Int,
    selectedIndex: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), // TODO think about it
    dotSize: Dp = dimensionResource(R.dimen.dot_indicator_dot_size)
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dot_indicator_space)),
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(dotCount) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}