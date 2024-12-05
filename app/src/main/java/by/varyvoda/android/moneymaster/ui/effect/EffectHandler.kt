package by.varyvoda.android.moneymaster.ui.effect

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class EffectHandlerRegistry(handlers: EffectHandlerRegistry.() -> Unit) {

    private val _handlersMap: MutableMap<KClass<out BaseEffect>, (effect: BaseEffect) -> Unit> =
        mutableMapOf()

    init {
        handlers(this)
    }

    @Suppress("UNCHECKED_CAST")
    fun <E : BaseEffect> addHandler(kClass: KClass<E>, handler: (effect: E) -> Unit) {
        _handlersMap[kClass] = handler as (BaseEffect) -> Unit
    }

    suspend fun from(effectFlow: Flow<BaseEffect>, scope: CoroutineScope) {
        effectFlow.collect {
            scope.launch {
                _handlersMap[it::class]?.invoke(it)
            }
        }
    }
}

inline fun <reified E : BaseEffect> EffectHandlerRegistry.handle(noinline handler: (effect: E) -> Unit) {
    addHandler(E::class, handler)
}

fun appEffects(handlers: EffectHandlerRegistry.() -> Unit): EffectHandlerRegistry {
    return EffectHandlerRegistry(handlers)
}
