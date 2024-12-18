@file:OptIn(ExperimentalContracts::class)

package by.varyvoda.android.moneymaster.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun <T> Flow<T?>.notNull() = map { it!! }

@Composable
fun <T> Flow<T?>.valueOrNull() = collectAsState(null).value

fun anyNull(value1: Any?, value2: Any?): Boolean {
    contract {
        returns(false) implies (value1 != null && value2 != null)
    }
    return value1 == null
            || value2 == null
}

fun anyNull(value1: Any?, value2: Any?, value3: Any?): Boolean {
    contract {
        returns(false) implies (value1 != null && value2 != null && value3 != null)
    }
    return value1 == null
            || value2 == null
            || value3 == null
}

fun anyNull(value1: Any?, value2: Any?, value3: Any?, value4: Any?): Boolean {
    contract {
        returns(false) implies (value1 != null && value2 != null && value3 != null && value4 != null)
    }
    return value1 == null
            || value2 == null
            || value3 == null
            || value4 == null
}

fun anyNull(vararg values: Any?): Boolean {
    return values.any { it == null }
}


fun allNull(value1: Any?, value2: Any?): Boolean {
    return value1 == null
            && value2 == null
}

fun allNull(value1: Any?, value2: Any?, value3: Any?): Boolean {
    return value1 == null
            && value2 == null
            && value3 == null
}

fun allNull(value1: Any?, value2: Any?, value3: Any?, value4: Any?): Boolean {
    return value1 == null
            && value2 == null
            && value3 == null
            && value4 == null
}

fun allNull(vararg values: Any?): Boolean = values.all { it == null }


fun anyNotNull(value1: Any?, value2: Any?): Boolean {
    return !allNull(value1, value2)
}

fun anyNotNull(value1: Any?, value2: Any?, value3: Any?): Boolean {
    return !allNull(value1, value2, value3)
}

fun anyNotNull(value1: Any?, value2: Any?, value3: Any?, value4: Any?): Boolean {
    return !allNull(value1, value2, value3, value4)
}

fun anyNotNull(vararg values: Any?): Boolean = !allNull(values = values)


fun allNotNull(value1: Any?, value2: Any?): Boolean {
    return !anyNull(value1, value2)
}

fun allNotNull(value1: Any?, value2: Any?, value3: Any?): Boolean {
    return !anyNull(value1, value2, value3)
}

fun allNotNull(value1: Any?, value2: Any?, value3: Any?, value4: Any?): Boolean {
    return !anyNull(value1, value2, value3, value4)
}

fun allNotNull(vararg values: Any?): Boolean = !anyNull(values = values)


