package by.varyvoda.android.moneymaster.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.ui.effect.BaseEffect
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    companion object {
        const val DEFAULT_TIMEOUT_MILLIS = 5000L
    }

    private val exceptionHandler = CoroutineExceptionHandler(::onError)

    private val _effect = Channel<BaseEffect>(Channel.BUFFERED)
    val effect: Flow<BaseEffect> = _effect.receiveAsFlow()

    open fun onError(context: CoroutineContext, throwable: Throwable) = Unit

    protected fun emitEffect(effect: BaseEffect) {
        launchOnMain {
            _effect.send(effect)
        }
    }

    protected fun launchOnMain(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler, block = block)
    }

    protected fun navigateTo(route: String) {
        emitEffect(NavigateToEffect(route))
    }

    protected fun navigateUp() {
        emitEffect(NavigateUpEffect)
    }

    protected fun <T> Flow<T>.stateInThis(initialValue: T) =
        this.stateIn(
            scope = this@BaseViewModel.viewModelScope,
            started = SharingStarted.WhileSubscribed(DEFAULT_TIMEOUT_MILLIS),
            initialValue = initialValue
        )

    protected fun <T> Flow<List<T>>.stateInThis(initialValue: List<T> = listOf()) =
        this.stateIn(
            scope = this@BaseViewModel.viewModelScope,
            started = SharingStarted.WhileSubscribed(DEFAULT_TIMEOUT_MILLIS),
            initialValue = initialValue
        )

    protected fun <K, V> Flow<Map<K, V>>.stateInThis(initialValue: Map<K, V> = mapOf()) =
        this.stateIn(
            scope = this@BaseViewModel.viewModelScope,
            started = SharingStarted.WhileSubscribed(DEFAULT_TIMEOUT_MILLIS),
            initialValue = initialValue
        )
}