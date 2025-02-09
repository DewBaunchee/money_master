package by.varyvoda.android.moneymaster.data.model.domain

typealias Id = Long

const val DEFAULT_ID = 0L

val Id.isDefault get() = this == DEFAULT_ID
