package by.varyvoda.android.moneymaster.ui.base

import by.varyvoda.android.moneymaster.ui.effect.BaseEffect

data class NavigateToEffect(val route: String) : BaseEffect()

data class NavigateBackToEffect(
    val destination: String,
    val inclusive: Boolean = false,
) : BaseEffect()

object NavigateBackEffect : BaseEffect()

object NavigateUpEffect : BaseEffect()