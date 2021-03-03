package com.rixspi.common.presentation

import com.airbnb.mvrx.*
import com.rixspi.domain.Error
import com.rixspi.domain.Result
import com.rixspi.domain.fold
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.IllegalStateException
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KProperty1

open class BaseViewModel<S : MavericksState>(
    private val initialState: S
) : MavericksViewModel<S>(initialState) {

    private fun <T, A> Result<T>.toAsync(mapper: ((T) -> A) ): Async<A> = fold(
            error = {
                // TODO Change this hardcoded exception
                Fail(IllegalStateException())
            },
            success = {
                Success(mapper(it))
            }
        )

    @OptIn(InternalMavericksApi::class)
    protected fun <A, T : Result<A>, V> Flow<T>.execute(
        dispatcher: CoroutineDispatcher? = null,
        retainValue: KProperty1<S, Async<V>>? = null,
        mapper: ((A) -> V),
        reducer: S.(Async<V>) -> S
    ): Job {
        val blockExecutions = config.onExecute(this@BaseViewModel)
        if (blockExecutions != MavericksViewModelConfig.BlockExecutions.No) {
            if (blockExecutions == MavericksViewModelConfig.BlockExecutions.WithLoading) {
                setState { reducer(Loading(value = retainValue?.get(this)?.invoke())) }
            }
            // Simulate infinite loading
            return viewModelScope.launch { delay(Long.MAX_VALUE) }
        }

        setState { reducer(Loading(value = retainValue?.get(this)?.invoke())) }
        return catch { error -> setState { reducer(Fail(error, value = retainValue?.get(this)?.invoke())) } }
            .onEach { value -> setState { reducer(value.toAsync(mapper)) } }
            .launchIn(viewModelScope + (dispatcher ?: EmptyCoroutineContext))
    }


    private fun <V> handleError(error: Error, stateReducer: S.(Async<V>) -> S) {
        // TODO Hardcoded error
        setState { stateReducer(Fail(IllegalStateException("Remember to change this"))) }
    }

    private fun <T : Any, V> handleResult(
        result: Result<T>,
        mapper: ((T) -> V),
        stateReducer: S.(Async<V>) -> S
    ) {
        result.fold(
            error = { handleError(it, stateReducer) },
            success = {
                val success = Success(mapper(it))
                setState { stateReducer(success) }
            }
        )
    }

    private fun <T : Any> handleResult(
        result: Result<T>,
        stateReducer: S.(Async<T>) -> S
    ) {
        result.fold(
            error = { handleError(it, stateReducer) },
            success = {
                val success = Success(it)
                setState { stateReducer(success) }
            }
        )
    }

}