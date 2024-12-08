package by.varyvoda.android.moneymaster.ui.base

import by.varyvoda.android.moneymaster.ui.effect.BaseEffect

data class NavigateToEffect(val route: Any) : BaseEffect()

data class NavigateBackToEffect(
    val destination: Any,
    val inclusive: Boolean = false,
) : BaseEffect()

object NavigateBackEffect : BaseEffect()

object NavigateUpEffect : BaseEffect()